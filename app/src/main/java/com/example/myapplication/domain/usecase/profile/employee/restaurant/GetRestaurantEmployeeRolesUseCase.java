package com.example.myapplication.domain.usecase.profile.employee.restaurant;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.domain.model.profile.UserEmployeeModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetRestaurantEmployeeRolesUseCase {
    public Single<List<UserEmployeeModel>> invoke(){
        return ProfileEmployeeDI.restaurantEmployee.getRestaurantEmployeeRoles().map(restaurantEmployeeDtos ->
                restaurantEmployeeDtos.stream()
                        .map(restaurantEmployeeDto -> new UserEmployeeModel(
                                restaurantEmployeeDto.getRole(),
                                restaurantEmployeeDto.getRestaurantId(),
                                restaurantEmployeeDto.getLocationId()))
                        .collect(Collectors.toList()));
    }
}
