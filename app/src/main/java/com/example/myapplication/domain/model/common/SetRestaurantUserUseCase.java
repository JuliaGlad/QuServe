package com.example.myapplication.domain.model.common;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class SetRestaurantUserUseCase {
    public Completable invoke(String restaurantId, String restaurantName){
        return DI.restaurantRepository.setRestaurantUser(restaurantId, restaurantName);
    }
}
