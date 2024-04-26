package com.example.myapplication.presentation.restaurantOrder.dishDetails;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.domain.model.restaurant.menu.RequiredChoiceModel;
import com.example.myapplication.domain.model.restaurant.menu.ToppingsModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.model.DishDetailsStateModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.model.RequiredChoiceDishDetailsModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.state.DishDetailsState;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;
import com.example.myapplication.presentation.restaurantOrder.CartDishModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.model.RequiredChoiceOrderDishDetailsModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.model.RestaurantOrderDishDetailsModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.state.RestaurantOrderDishDetailsState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantOrderDishDetailsViewModel extends ViewModel {

    private final MutableLiveData<RestaurantOrderDishDetailsState> _state = new MutableLiveData<>(new RestaurantOrderDishDetailsState.Loading());
    LiveData<RestaurantOrderDishDetailsState> state = _state;

    String name, price, timeCooking, ingredients, weightOrCount;
    Uri imageUri;
    List<String> ingredientsToRemove;
    List<String> toppingsNames = new ArrayList<>();
    List<ToppingsModel> toppingsModels;
    List<VariantsModel> toppings = new ArrayList<>();
    List<RequiredChoiceOrderDishDetailsModel> models = new ArrayList<>();

    public void getDishData(String restaurantId, String categoryId, String dishId) {
        RestaurantDI.getSingleDishByIdUseCase.invoke(restaurantId, categoryId, dishId)
                .flatMap(dishMenuOwnerModel -> {

                    name = dishMenuOwnerModel.getName();
                    ingredients = dishMenuOwnerModel.getIngredients();
                    weightOrCount = dishMenuOwnerModel.getWeightCount();
                    price = dishMenuOwnerModel.getPrice();
                    timeCooking = dishMenuOwnerModel.getEstimatedTimeCooking();
                    ingredientsToRemove = dishMenuOwnerModel.getToRemove();

                    return RestaurantDI.getSingleDishImageUseCase.invoke(restaurantId, dishId);
                })
                .flatMap(imageModel -> {
                    imageUri = imageModel.getImageUri();
                    return RestaurantDI.getRequiredChoicesUseCase.invoke(restaurantId, categoryId, dishId);
                }).flatMap(requiredChoiceModels -> {
                    for (RequiredChoiceModel current : requiredChoiceModels) {
                        models.add(new RequiredChoiceOrderDishDetailsModel(current.getId(), current.getName(), current.getVariantsName()));
                    }
                    return RestaurantDI.getToppingsUseCase.invoke(restaurantId, categoryId, dishId);
                }).flatMap(toppingsModels -> {
                    this.toppingsModels = toppingsModels;
                    for (ToppingsModel current : toppingsModels) {
                        toppingsNames.add(current.getName());
                    }
                    return RestaurantDI.getToppingsImagesUseCase.invoke(restaurantId, dishId, toppingsNames);
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
                        _state.postValue(new RestaurantOrderDishDetailsState.Success(new RestaurantOrderDishDetailsModel(
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

    public void addToCart(String restaurantId, CartDishModel model) {
        RestaurantDI.addDishToCartUseCase.invoke(restaurantId, model);
    }
}