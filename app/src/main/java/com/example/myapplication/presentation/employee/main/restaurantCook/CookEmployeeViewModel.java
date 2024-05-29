package com.example.myapplication.presentation.employee.main.restaurantCook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;
import com.example.myapplication.di.restaurant.RestaurantUserDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CookEmployeeViewModel extends ViewModel {

    private final MutableLiveData<String> _name = new MutableLiveData<>(null);
    LiveData<String> name = _name;

    public void getRestaurantName(String restaurantId) {
        RestaurantUserDI.getRestaurantNameByIdsUseCase.invoke(restaurantId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        _name.setValue(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
