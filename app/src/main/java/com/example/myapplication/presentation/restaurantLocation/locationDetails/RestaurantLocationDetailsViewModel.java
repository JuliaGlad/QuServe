package com.example.myapplication.presentation.restaurantLocation.locationDetails;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantLocationDetailsViewModel extends ViewModel {

    private final MutableLiveData<Uri> _cookQrCode = new MutableLiveData<>(Uri.EMPTY);
    LiveData<Uri> cookQrCode = _cookQrCode;

    private final MutableLiveData<Uri> _waiterQrCode = new MutableLiveData<>(Uri.EMPTY);
    LiveData<Uri> waiterQrCode = _waiterQrCode;

    public void getCookQrCode(String locationId){
        RestaurantDI.getCookQrCodeUseCase.invoke(locationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Uri>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Uri uri) {
                        _cookQrCode.postValue(uri);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getWaiterQrCode(String locationId){
        RestaurantDI.getWaiterQrCodeUseCase.invoke(locationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Uri>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Uri uri) {
                        _waiterQrCode.postValue(uri);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}