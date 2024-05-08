package com.example.myapplication.presentation.restaurantMenu.dishDetails;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.domain.model.restaurant.menu.RequiredChoiceModel;
import com.example.myapplication.domain.model.restaurant.menu.ToppingsModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.model.DishDetailsStateModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.model.RequiredChoiceDishDetailsModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.state.DishDetailsState;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DishDetailsViewModel extends ViewModel {

    private final MutableLiveData<DishDetailsState> _state = new MutableLiveData<>(new DishDetailsState.Loading());
    LiveData<DishDetailsState> state = _state;

    private final MutableLiveData<Boolean> _isUpdated = new MutableLiveData<>(false);
    LiveData<Boolean> isUpdated = _isUpdated;

    String name, price, timeCooking, ingredients, weightOrCount;
    Uri imageUri;
    List<String> ingredientsToRemove;
    List<String> toppingsNames = new ArrayList<>();
    List<ToppingsModel> toppingsModels;
    List<VariantsModel> toppings = new ArrayList<>();
    List<RequiredChoiceDishDetailsModel> models = new ArrayList<>();

    public void getDishData(String restaurantId, String categoryId, String dishId) {
        RestaurantMenuDI.getSingleDishByIdUseCase.invoke(restaurantId, categoryId, dishId)
                .flatMap(dishMenuOwnerModel -> {

                    name = dishMenuOwnerModel.getName();
                    ingredients = dishMenuOwnerModel.getIngredients();
                    weightOrCount = dishMenuOwnerModel.getWeightCount();
                    price = dishMenuOwnerModel.getPrice();
                    timeCooking = dishMenuOwnerModel.getEstimatedTimeCooking();
                    ingredientsToRemove = dishMenuOwnerModel.getToRemove();

                    return RestaurantMenuDI.getSingleDishImageUseCase.invoke(restaurantId, dishId);
                })
                .flatMap(imageModel -> {
                    imageUri = imageModel.getImageUri();
                    return RestaurantMenuDI.getRequiredChoicesUseCase.invoke(restaurantId, categoryId, dishId);
                }).flatMap(requiredChoiceModels -> {
                    for (RequiredChoiceModel current : requiredChoiceModels) {
                        models.add(new RequiredChoiceDishDetailsModel(current.getId(), current.getName(), current.getVariantsName()));
                    }
                    return RestaurantMenuDI.getToppingsUseCase.invoke(restaurantId, categoryId, dishId);
                }).flatMap(toppingsModels -> {
                    this.toppingsModels = toppingsModels;
                    for (ToppingsModel current : toppingsModels) {
                        toppingsNames.add(current.getName());
                    }
                    return RestaurantMenuDI.getToppingsImagesUseCase.invoke(restaurantId, dishId, toppingsNames);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<ImageTaskNameModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<ImageTaskNameModel> imageTaskModels) {
                        for (ToppingsModel model : toppingsModels) {
                            for (ImageTaskNameModel image : imageTaskModels) {
                                if (image.getName().equals(model.getName())) {
                                    toppings.add(new VariantsModel(
                                            model.getName(), model.getPrice(), image.getTask()
                                    ));
                                    break;
                                }
                            }
                        }
                        _state.postValue(new DishDetailsState.Success(new DishDetailsStateModel(
                                name, price, timeCooking, ingredients,
                                weightOrCount, ingredientsToRemove, toppings,
                                models, imageUri
                        )));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public void saveData(String restaurantId, String categoryId, String dishId, String name, String ingredients, String price, String weightOrCount) {
        RestaurantMenuDI.updateDishDataUseCase.invoke(restaurantId, categoryId, dishId, name, ingredients, price, weightOrCount)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isUpdated.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
