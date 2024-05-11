package com.example.myapplication.domain.usecase.restaurant.employee;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;
import com.example.myapplication.domain.model.restaurant.RestaurantEmployeeModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetEmployeesUseCase {
    public Single<List<RestaurantEmployeeModel>> invoke(String restaurantId, String locationId) {
        return RestaurantEmployeeDI.restaurantEmployeesRepository.getEmployees(restaurantId, locationId).map(employeeRestaurantDtos ->
                employeeRestaurantDtos.stream()
                        .map(employeeRestaurantDto -> new RestaurantEmployeeModel(
                                employeeRestaurantDto.getUserId(),
                                employeeRestaurantDto.getName(),
                                employeeRestaurantDto.getRole()
                        ))
                        .collect(Collectors.toList()));
    }
}
