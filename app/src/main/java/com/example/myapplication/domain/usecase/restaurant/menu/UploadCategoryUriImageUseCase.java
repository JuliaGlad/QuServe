package com.example.myapplication.domain.usecase.restaurant.menu;

import android.net.Uri;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadCategoryUriImageUseCase {
    public Completable invoke(Uri uri, String restaurantId, String categoryId){
        return DI.restaurantOwnerRepository.uploadUriCategoryImage(uri, restaurantId, categoryId);
    }
}
