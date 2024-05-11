package com.example.myapplication.domain.usecase.restaurant.employee;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteEmployeeUseCase {
    public Completable invoke(String restaurantId, String locationId, String userId, String role){
        return RestaurantEmployeeDI.restaurantEmployeesRepository.deleteRestaurantEmployee(restaurantId, locationId, userId, role);
    }
}
