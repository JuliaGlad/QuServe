package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Single;

public class AddRestaurantTablesUseCase {
    public Single<String> invoke(String restaurantId, String locationId, String tableId, String number){
        return DI.restaurantOwnerRepository.addTable(restaurantId, locationId, tableId, number);
    }
}
