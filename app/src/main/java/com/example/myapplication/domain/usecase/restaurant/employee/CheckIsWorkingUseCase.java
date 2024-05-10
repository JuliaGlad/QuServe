package com.example.myapplication.domain.usecase.restaurant.employee;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.core.Single;

public class CheckIsWorkingUseCase {
    public Single<Boolean> invoke(String restaurantId, String locationId){
        return RestaurantEmployeeDI.restaurantEmployeesRepository.checkIsWorking(restaurantId, locationId);
    }
}
