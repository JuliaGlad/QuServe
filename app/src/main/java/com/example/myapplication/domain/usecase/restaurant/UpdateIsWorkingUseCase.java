package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateIsWorkingUseCase {
    public Completable invoke(String restaurantId, String locationId, boolean isWorking){
        return RestaurantEmployeeDI.restaurantEmployeesRepository.updateIsWorking(restaurantId, locationId, isWorking);
    }
}
