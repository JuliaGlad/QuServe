package com.example.myapplication.domain.usecase.restaurant.restaurantUser.restaurantImages;

import android.net.Uri;
import com.example.myapplication.di.restaurant.RestaurantUserDI;

import io.reactivex.rxjava3.core.Completable;

public class UploadRestaurantLogoUseCase {
    public Completable invoke(Uri uri, String restaurantId){
        return RestaurantUserDI.restaurantImages.uploadRestaurantLogoToFireStorage(uri, restaurantId);
    }
}
