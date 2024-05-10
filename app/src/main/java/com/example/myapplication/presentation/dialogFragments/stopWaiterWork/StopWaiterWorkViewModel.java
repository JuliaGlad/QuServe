package com.example.myapplication.presentation.dialogFragments.stopWaiterWork;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StopWaiterWorkViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isStopped = new MutableLiveData<>(false);
    LiveData<Boolean> isStopped = _isStopped;

    public void updateIsWorking(String restaurantId, String locationId){
        RestaurantEmployeeDI.updateIsWorkingUseCase.invoke(restaurantId, locationId, false)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isStopped.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}
