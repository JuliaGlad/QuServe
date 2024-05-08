package com.example.myapplication.domain.usecase.restaurant.tables;

import com.example.myapplication.di.restaurant.RestaurantTableDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteTableUseCase {
    public Completable invoke(String restaurantId, String locationId, String tableId){
        return RestaurantTableDI.restaurantTablesRepository.deleteTable(restaurantId, locationId, tableId);
    }
}
