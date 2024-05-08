package com.example.myapplication.domain.usecase.restaurant.menu.images;

import android.net.Uri;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class UploadCategoryUriImageUseCase {
    public Completable invoke(Uri uri, String restaurantId, String categoryId){
        return RestaurantMenuDI.menuImages.uploadUriCategoryImage(uri, restaurantId, categoryId);
    }
}
