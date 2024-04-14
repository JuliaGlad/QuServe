package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Single;

public class CreateRestaurantLocationDocumentUseCase {
    public Single<String> invoke(String restaurantId, String locationId, String location, String city){
        return DI.restaurantOwnerRepository.createRestaurantLocationDocument(restaurantId, locationId, location, city);
    }
}
