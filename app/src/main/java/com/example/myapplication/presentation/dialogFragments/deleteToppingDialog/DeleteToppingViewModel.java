package com.example.myapplication.presentation.dialogFragments.deleteToppingDialog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeleteToppingViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isDeleted = new MutableLiveData<>(false);
    LiveData<Boolean> isDeleted = _isDeleted;

    public void deleteTopping(String restaurantId, String categoryId, String dishId, String name) {
        RestaurantDI.deleteToppingUseCase.invoke(restaurantId, categoryId, dishId, name)
                .concatWith(RestaurantDI.deleteToppingImageUseCase.invoke(restaurantId, dishId, name))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isDeleted.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
