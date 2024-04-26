package com.example.myapplication.domain.usecase.restaurant;

import android.net.Uri;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadRestaurantLogoUseCase {
    public Completable invoke(Uri uri, String restaurantId){
        return DI.restaurantRepository.uploadRestaurantLogoToFireStorage(uri, restaurantId);
    }
}
