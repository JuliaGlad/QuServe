package com.example.myapplication.domain.usecase.restaurant.restaurantUser;

import com.example.myapplication.di.restaurant.RestaurantUserDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateRestaurantDataUseCase {
    public Completable invoke(String restaurantId, String restaurantName, String phone){
        return RestaurantUserDI.restaurantUserRepository.updateRestaurantData(restaurantId, restaurantName, phone);
    }
}
