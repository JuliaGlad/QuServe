package com.example.myapplication.presentation.dialogFragments.deleteRestaurantEmployee;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeleteRestaurantEmployeeDialogViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isDeleted = new MutableLiveData<>(false);
    LiveData<Boolean> isDeleted = _isDeleted;

    public void deleteEmployee(String restaurantId, String locationId, String userId, String role) {
        RestaurantEmployeeDI.deleteEmployeeUseCase.invoke(restaurantId, locationId, userId, role)
                .concatWith(ProfileDI.deleteRestaurantEmployeeRoleUseCase.invoke(restaurantId, userId))
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