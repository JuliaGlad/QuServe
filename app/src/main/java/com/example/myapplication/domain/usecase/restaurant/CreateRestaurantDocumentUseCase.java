package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class CreateRestaurantDocumentUseCase {
    public Completable invoke(String restaurantId, String restaurantName, String email, String phone){
        return DI.restaurantOwnerRepository.createRestaurantDocument(restaurantId, restaurantName, email, phone);
    }
}
