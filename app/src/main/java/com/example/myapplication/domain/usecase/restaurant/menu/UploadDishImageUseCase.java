package com.example.myapplication.domain.usecase.restaurant.menu;

import android.net.Uri;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadDishImageUseCase {
    public Completable invoke(String restaurantId, String dishId,Uri uri){
        return DI.restaurantRepository.uploadDishImage(restaurantId, dishId, uri);
    }
}
