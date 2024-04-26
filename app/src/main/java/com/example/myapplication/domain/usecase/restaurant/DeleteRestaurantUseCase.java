package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteRestaurantUseCase {
    public Completable invoke(String restaurantId){
        return DI.restaurantRepository.deleteRestaurant(restaurantId);
    }
}
