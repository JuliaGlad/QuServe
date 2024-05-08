package com.example.myapplication.domain.usecase.restaurant.tables;

import com.example.myapplication.di.restaurant.RestaurantTableDI;

import io.reactivex.rxjava3.core.Single;

public class GetSingleTableByIdUseCase {
    public Single<String> invoke(String restaurantId, String locationId, String tableId) {
        return RestaurantTableDI.restaurantTablesRepository.getSingleTableById(restaurantId, locationId, tableId);
    }
}
