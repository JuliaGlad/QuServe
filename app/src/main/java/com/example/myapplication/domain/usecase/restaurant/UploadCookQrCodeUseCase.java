package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadCookQrCodeUseCase {
    public Completable invoke(String locationId, byte[] bytes){
        return DI.restaurantRepository.uploadCookQrCode(locationId, bytes);
    }
}
