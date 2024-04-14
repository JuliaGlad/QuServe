package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateRestaurantDataUseCase {
    public Completable invoke(String restaurantId, String restaurantName, String phone){
        return DI.restaurantOwnerRepository.updateRestaurantData(restaurantId, restaurantName, phone);
    }
}
