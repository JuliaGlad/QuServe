package com.example.myapplication.presentation.restaurantMenu;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;
import com.example.myapplication.domain.model.restaurant.menu.CategoryModel;
import com.example.myapplication.domain.model.restaurant.menu.DishMenuOwnerModel;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.CategoryMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.DishMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.state.RestaurantMenuState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantMenuViewModel extends ViewModel {

    private final MutableLiveData<List<DishMenuModel>> _newCategory = new MutableLiveData<>(null);
    LiveData<List<DishMenuModel>> newCategory = _newCategory;

    private final MutableLiveData<List<CategoryMenuModel>> _categories = new MutableLiveData<>(null);
    LiveData<List<CategoryMenuModel>> categories = _categories;

    private final MutableLiveData<RestaurantMenuState> _state = new MutableLiveData<>(new RestaurantMenuState.Loading());
    LiveData<RestaurantMenuState> state = _state;

    public void getMenuCategories(String restaurantId) {
        List<CategoryModel> models = new ArrayList<>();
        RestaurantDI.getCategoriesUseCase.invoke(restaurantId)
                .flatMap(categoryModels -> {
                    List<String> categoryNames = new ArrayList<>();
                    if (categoryModels.size() > 0) {
                        for (CategoryModel current : categoryModels) {
                            models.add(current);
                            categoryNames.add(current.getName());
                        }
                    }
                    return RestaurantDI.getCategoriesImagesUseCase.invoke(restaurantId, categoryNames);
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
                                    Log.i("Results", "Image: " + model.getName() + "Category: " + currentModel.getName());
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

                    }
                });
    }

    public void getCategoryDishes(String restaurantId, String categoryId, boolean isDefault) {
        List<DishMenuOwnerModel> dishes = new ArrayList<>();
        RestaurantDI.getDishesMenuOwnerModelsUseCase.invoke(restaurantId, categoryId)
                .flatMap(dishMenuOwnerModels -> {
                    dishes.addAll(dishMenuOwnerModels);
                    return RestaurantDI.getDishesImagesUseCase.invoke(restaurantId, dishes);
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

                    }
                });
    }

//    List<DishMenuModel> models = new ArrayList<>();
//                        for (DishMenuOwnerModel current : dishMenuOwnerModels) {
//        models.add(new DishMenuModel(
//                current.getDishId(),
//                current.getName(),
//                current.getPrice(),
//                current.getWeightCount()
//        ));
//    }
//                        if (categories != null){
//        _state.postValue(new RestaurantMenuState.Success(new RestaurantMenuStateModel(models, categories)));
//    } else {
//        _newCategory.postValue(models);
//    }
}