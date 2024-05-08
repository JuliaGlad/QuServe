package com.example.myapplication.domain.usecase.restaurant.locations;

import com.example.myapplication.di.restaurant.RestaurantLocationDI;

import io.reactivex.rxjava3.core.Single;

public class CreateRestaurantLocationDocumentUseCase {
    public Single<String> invoke(String restaurantId, String locationId, String location, String city){
        return RestaurantLocationDI.restaurantLocationRepository.createRestaurantLocationDocument(restaurantId, locationId, location, city);
    }
}
