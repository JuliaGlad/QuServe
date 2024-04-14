package com.example.myapplication.presentation.dialogFragments.deleteRestaurant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.RestaurantDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeleteRestaurantViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isDeleted = new MutableLiveData<>();
    LiveData<Boolean> isDeleted = _isDeleted;

    public void deleteRestaurant(String restaurantId){
        RestaurantDI.deleteRestaurantUseCase.invoke(restaurantId)
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
