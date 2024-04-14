package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Single;

public class GetSingleTableByIdUseCase {
    public Single<String> invoke(String restaurantId, String locationId, String tableId) {
        return DI.restaurantOwnerRepository.getSingleTableById(restaurantId, locationId, tableId);
    }
}
