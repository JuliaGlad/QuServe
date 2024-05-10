package com.example.myapplication.domain.usecase.restaurant.employee;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class AddWaiterUseCase {
    public Completable invoke(String waiterPath){
        return RestaurantEmployeeDI.restaurantEmployeesRepository.addWaiter(waiterPath);
    }
}
