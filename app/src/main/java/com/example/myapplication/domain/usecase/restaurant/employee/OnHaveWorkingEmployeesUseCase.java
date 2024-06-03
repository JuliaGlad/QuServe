package com.example.myapplication.domain.usecase.restaurant.employee;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.core.Single;

public class OnHaveWorkingEmployeesUseCase {
    public Single<Boolean> invoke(String tablePath){
        return RestaurantEmployeeDI.restaurantEmployeesRepository.checkHaveWorkingEmployees(tablePath);
    }
}
