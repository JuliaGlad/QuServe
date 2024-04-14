package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteTableUseCase {
    public Completable invoke(String restaurantId, String locationId, String tableId){
        return DI.restaurantOwnerRepository.deleteTable(restaurantId, locationId, tableId);
    }
}
