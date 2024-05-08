package com.example.myapplication.domain.model.common;

import com.example.myapplication.data.repository.restaurant.RestaurantUserRepository;
import com.example.myapplication.di.DI;
import com.example.myapplication.di.restaurant.RestaurantUserDI;

import io.reactivex.rxjava3.core.Completable;

public class SetRestaurantUserUseCase {
    public Completable invoke(String restaurantId, String restaurantName){
        return RestaurantUserDI.restaurantUserRepository.setRestaurantUser(restaurantId, restaurantName);
    }
}
