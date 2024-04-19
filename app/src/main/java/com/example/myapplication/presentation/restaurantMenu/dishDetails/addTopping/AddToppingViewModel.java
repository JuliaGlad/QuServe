package com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping;

import static com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.AddToppingArguments.image;
import static com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.AddToppingArguments.name;
import static com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.AddToppingArguments.price;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddToppingViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isComplete = new MutableLiveData<>(false);
    LiveData<Boolean> isComplete = _isComplete;

    public void setArgumentsNull() {
        price = null;
        name = null;
        image = null;
    }

    public void initData(String restaurantId, String categoryId, String dishId) {
        RestaurantDI.addToppingUseCase.invoke(restaurantId, categoryId, dishId, name, price)
                .andThen(RestaurantDI.uploadToppingImage.invoke(name, image, restaurantId, dishId))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isComplete.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}