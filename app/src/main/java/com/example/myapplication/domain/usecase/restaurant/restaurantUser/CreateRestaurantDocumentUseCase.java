package com.example.myapplication.domain.usecase.restaurant.restaurantUser;

import com.example.myapplication.di.restaurant.RestaurantUserDI;

import io.reactivex.rxjava3.core.Completable;

public class CreateRestaurantDocumentUseCase {
    public Completable invoke(String restaurantId, String restaurantName, String email, String phone){
        return RestaurantUserDI.restaurantUserRepository.createRestaurantDocument(restaurantId, restaurantName, email, phone);
    }
}
