package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable.successfullyAdded;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantTableDI;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable.successfullyAdded.state.TableAddedState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TableSuccessfullyAddedViewModel extends ViewModel {

    private final MutableLiveData<TableAddedState> _state = new MutableLiveData<>();
    LiveData<TableAddedState> state = _state;

    public void getTableQrCode(String tableId) {
        RestaurantTableDI.getTableQrCodeJpgUseCase.invoke(tableId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Uri>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Uri uri) {
                        _state.postValue(new TableAddedState.Success(uri));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}