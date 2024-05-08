package com.example.myapplication.domain.usecase.restaurant.tables;

import com.example.myapplication.di.restaurant.RestaurantTableDI;

import io.reactivex.rxjava3.core.Single;

public class AddRestaurantTablesUseCase {
    public Single<String> invoke(String restaurantId, String locationId, String tableId, String number){
        return RestaurantTableDI.restaurantTablesRepository.addTable(restaurantId, locationId, tableId, number);
    }
}
