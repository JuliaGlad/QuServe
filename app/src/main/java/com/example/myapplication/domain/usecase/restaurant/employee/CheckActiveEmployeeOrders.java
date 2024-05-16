package com.example.myapplication.domain.usecase.restaurant.employee;

import com.example.myapplication.data.repository.restaurant.RestaurantEmployeesRepository;
import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.core.Single;

public class CheckActiveEmployeeOrders {
    public Single<Boolean> invoke(String restaurantId, String locationId){
        return RestaurantEmployeeDI.restaurantEmployeesRepository.haveOrders(restaurantId, locationId);
    }
}
