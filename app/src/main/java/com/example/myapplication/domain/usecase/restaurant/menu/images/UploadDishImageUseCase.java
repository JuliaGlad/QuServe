package com.example.myapplication.domain.usecase.restaurant.menu.images;

import android.net.Uri;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class UploadDishImageUseCase {
    public Completable invoke(String restaurantId, String dishId,Uri uri){
        return RestaurantMenuDI.menuImages.uploadDishImage(restaurantId, dishId, uri);
    }
}
