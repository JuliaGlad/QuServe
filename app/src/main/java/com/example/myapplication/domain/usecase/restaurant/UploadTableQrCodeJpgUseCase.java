package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadTableQrCodeJpgUseCase {
    public Completable invoke(String tableId, byte[] data){
        return DI.restaurantOwnerRepository.uploadTableQrCodeFireStorage(tableId, data);
    }
}
