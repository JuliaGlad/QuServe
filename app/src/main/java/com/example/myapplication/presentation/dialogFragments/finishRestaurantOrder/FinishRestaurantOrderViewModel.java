package com.example.myapplication.presentation.dialogFragments.finishRestaurantOrder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.repository.profile.ProfileRepository;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.restaurant.RestaurantOrderDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FinishRestaurantOrderViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isFinished = new MutableLiveData<>();
    LiveData<Boolean> isFinished = _isFinished;

    public void finishOrder(String orderPath) {
        RestaurantOrderDI.finishOrderByPathUseCase.invoke(orderPath)
                .concatWith(ProfileDI.removeRestaurantUserOrderUseCase.invoke())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isFinished.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
