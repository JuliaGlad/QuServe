package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadCategoryBytesImageUseCase {
    public Completable invoke(byte[] bytes,  String restaurantId, String categoryName){
        return DI.restaurantRepository.uploadBytesCategoryImage(bytes, restaurantId, categoryName);
    }
}
