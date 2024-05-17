package com.example.myapplication.presentation.restaurantMenu.addDish;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.presentation.restaurantMenu.DishAddedModel;

import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddDishViewModel extends ViewModel {
    private final MutableLiveData<DishAddedModel> _onComplete = new MutableLiveData<>(null);
    public LiveData<DishAddedModel> onComplete = _onComplete;

    public void initDishData(String restaurantId, String categoryId) {
        String dishId = generateId();
        RestaurantMenuDI.addDishUseCase.invoke(restaurantId, categoryId, dishId, DishArguments.name, DishArguments.ingredients, DishArguments.weightCount, DishArguments.price, DishArguments.timeCooking)
                .andThen(RestaurantMenuDI.uploadDishImageUseCase.invoke(restaurantId, dishId, DishArguments.imageUri))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _onComplete.postValue(new DishAddedModel(
                                dishId,
                                DishArguments.price,
                                DishArguments.name,
                                DishArguments.weightCount,
                                DishArguments.imageUri
                        ));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private String generateId() {
        Random rand = new Random();
        int id = rand.nextInt(90000000) + 10000000;
        return "@" + id;
    }

    public void setArgumentsNull() {
        DishArguments.name = null;
        DishArguments.imageUri = null;
        DishArguments.price = null;
        DishArguments.ingredients = null;
        DishArguments.timeCooking = null;
        DishArguments.weightCount = null;
    }
}