package com.example.myapplication.presentation.restaurantMenu;

import static com.example.myapplication.presentation.utils.constants.Utils.URI;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.QueueDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.domain.model.restaurant.menu.CategoryModel;
import com.example.myapplication.domain.model.restaurant.menu.DishMenuOwnerModel;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.CategoryMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.DishMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.state.RestaurantMenuState;
import com.example.myapplication.presentation.restaurantMenu.model.CategoryImageNameModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantMenuViewModel extends ViewModel {

    private final MutableLiveData<List<DishMenuModel>> _newCategory = new MutableLiveData<>(null);
    LiveData<List<DishMenuModel>> newCategory = _newCategory;

    private final MutableLiveData<Bundle> _addedUri = new MutableLiveData<>(null);
    LiveData<Bundle> addedUri = _addedUri;

    private final MutableLiveData<List<CategoryMenuModel>> _categories = new MutableLiveData<>(null);
    LiveData<List<CategoryMenuModel>> categories = _categories;

    private final MutableLiveData<Integer> _categoryDeleted = new MutableLiveData<>(null);
    LiveData<Integer> categoryDeleted = _categoryDeleted;

    private final MutableLiveData<RestaurantMenuState> _state = new MutableLiveData<>(new RestaurantMenuState.Loading());
    LiveData<RestaurantMenuState> state = _state;

    private final MutableLiveData<Boolean> _isSignIn = new MutableLiveData<>(false);
    LiveData<Boolean> isSignIn = _isSignIn;

    public boolean checkUserID() {
        return ProfileDI.checkUserIdUseCase.invoke();
    }

    public void signInAnonymously() {
        QueueDI.signInAnonymouslyUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isSignIn.postValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        _state.postValue(new RestaurantMenuState.Error());
                    }
                });
    }

    public void getMenuCategories(String restaurantId) {
        List<CategoryModel> models = new ArrayList<>();
        RestaurantMenuDI.getCategoriesUseCase.invoke(restaurantId)
                .flatMap(categoryModels -> {
                    List<CategoryImageNameModel> categoryNames = new ArrayList<>();
                    if (!categoryModels.isEmpty()) {
                        for (CategoryModel current : categoryModels) {
                            models.add(current);
                            categoryNames.add(new CategoryImageNameModel(current.getName(), current.getDefaultImage()));
                        }
                    }
                    Log.d("Category", categoryNames.get(0).getName() + " " + categoryNames.get(0).getDefaultImage());
                    return RestaurantMenuDI.getCategoriesImagesUseCase.invoke(restaurantId, categoryNames);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<ImageTaskNameModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<ImageTaskNameModel> imageTaskIdModels) {
                        List<CategoryMenuModel> menuModels = new ArrayList<>();
                        for (CategoryModel currentModel : models) {
                            for (ImageTaskNameModel model : imageTaskIdModels) {
                                if (Objects.equals(model.getName(), currentModel.getName())) {
                                    menuModels.add(new CategoryMenuModel(
                                            currentModel.getCategoryId(),
                                            currentModel.getName(),
                                            model.getTask()
                                    ));
                                    break;
                                }
                            }
                        }
                        _categories.postValue(menuModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new RestaurantMenuState.Error());
                    }
                });
    }

    public void getCategoryDishes(String restaurantId, String categoryId, boolean isDefault) {
        List<DishMenuOwnerModel> dishes = new ArrayList<>();
        RestaurantMenuDI.getDishesMenuOwnerModelsUseCase.invoke(restaurantId, categoryId)
                .flatMap(dishMenuOwnerModels -> {
                    dishes.addAll(dishMenuOwnerModels);
                    return RestaurantMenuDI.getDishesImagesUseCase.invoke(restaurantId, dishes);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<ImageTaskNameModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<ImageTaskNameModel> imageTaskNameModels) {
                        List<DishMenuModel> menuModels = new ArrayList<>();
                        for (DishMenuOwnerModel currentModel : dishes) {
                            for (ImageTaskNameModel model : imageTaskNameModels) {
                                if (Objects.equals(model.getName(), currentModel.getDishId())) {
                                    menuModels.add(new DishMenuModel(
                                            currentModel.getDishId(),
                                            currentModel.getName(),
                                            currentModel.getPrice(),
                                            currentModel.getWeightCount(),
                                            model.getTask()
                                    ));
                                    break;
                                }
                            }
                        }
                        if (isDefault){
                            _state.postValue(new RestaurantMenuState.Success(menuModels));
                        } else {
                            _newCategory.postValue(menuModels);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new RestaurantMenuState.Error());
                    }
                });
    }

    public void setSuccess() {
        _state.postValue(new RestaurantMenuState.Success(Collections.emptyList()));
    }

    public void deleteCategory(int position, String restaurantId, String categoryId) {
        RestaurantMenuDI.deleteCategoryUseCase.invoke(restaurantId, categoryId)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _categoryDeleted.postValue(position);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Error delete category", e.getMessage());
                    }
                });
    }

    public void getImageDrawable(Bundle bundle, String image, Uri uri) {
        if (image != null){
            RestaurantMenuDI.getCategoryImageDrawableUseCase.invoke(image)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<Uri>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull Uri uri) {
                            bundle.putString(URI, String.valueOf(uri));
                            _addedUri.postValue(bundle);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            bundle.putString(URI, String.valueOf(uri));
            _addedUri.postValue(bundle);
        }
    }
}