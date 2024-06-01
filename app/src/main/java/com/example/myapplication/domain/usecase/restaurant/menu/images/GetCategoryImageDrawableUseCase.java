package com.example.myapplication.domain.usecase.restaurant.menu.images;

import android.net.Uri;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Single;

public class GetCategoryImageDrawableUseCase {
    public Single<Uri> invoke(String name){
        return RestaurantMenuDI.menuImages.getImageDrawableByName(name);
    }
}
