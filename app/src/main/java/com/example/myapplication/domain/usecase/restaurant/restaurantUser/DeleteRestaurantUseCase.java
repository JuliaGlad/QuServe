package com.example.myapplication.domain.usecase.restaurant.restaurantUser;

import com.example.myapplication.di.restaurant.RestaurantUserDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteRestaurantUseCase {
    public Completable invoke(String restaurantId){
        return RestaurantUserDI.restaurantUserRepository.deleteRestaurant(restaurantId);
    }
}
