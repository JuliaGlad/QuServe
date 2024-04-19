package com.example.myapplication.domain.usecase.restaurant.menu;

import android.net.Uri;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadToppingImage {
    public Completable invoke(String name, Uri uri, String restaurantId, String dishId){
        return DI.restaurantOwnerRepository.uploadToppingImage(name, uri, restaurantId, dishId);
    }
}
