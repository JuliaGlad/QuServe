package com.example.myapplication.domain.usecase.restaurant.employee;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class AddCookUseCase {
    public Completable invoke(String path){
        return RestaurantEmployeeDI.restaurantEmployeesRepository.addCook(path);
    }
}
