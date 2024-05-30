package com.example.myapplication.domain.usecase.restaurant.locations;

import com.example.myapplication.di.restaurant.RestaurantLocationDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteLocationUseCase {
    public Completable invoke(String restaurantId, String locationId){
        return RestaurantLocationDI.restaurantLocationRepository.deleteLocation(restaurantId, locationId);
    }
}
