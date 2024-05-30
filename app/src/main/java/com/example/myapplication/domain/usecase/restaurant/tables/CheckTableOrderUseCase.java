package com.example.myapplication.domain.usecase.restaurant.tables;

import com.example.myapplication.di.restaurant.RestaurantTableDI;

import io.reactivex.rxjava3.core.Single;

public class CheckTableOrderUseCase {
    public Single<Boolean> invoke(String tablePath){
        return RestaurantTableDI.restaurantTablesRepository.checkTableOrder(tablePath);
    }
}
