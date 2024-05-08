package com.example.myapplication.presentation.dialogFragments.deleteTable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantTableDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeleteTableViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isDeleted = new MutableLiveData<>(false);
    LiveData<Boolean> isDeleted = _isDeleted;

    public void deleteTable(String restaurantId, String locationId, String tableId) {
        RestaurantTableDI.deleteTableUseCase.invoke(restaurantId, locationId, tableId)
                .concatWith(RestaurantTableDI.deleteTableQrCodeJpgUseCase.invoke(tableId))
                .andThen(RestaurantTableDI.deleteTableQrCodePdfUseCase.invoke(tableId))
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
