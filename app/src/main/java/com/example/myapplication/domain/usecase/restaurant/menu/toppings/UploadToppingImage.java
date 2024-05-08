package com.example.myapplication.domain.usecase.restaurant.menu.toppings;

import android.net.Uri;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class UploadToppingImage {
    public Completable invoke(String name, Uri uri, String restaurantId, String dishId){
        return RestaurantMenuDI.toppings.uploadToppingImage(name, uri, restaurantId, dishId);
    }
}
