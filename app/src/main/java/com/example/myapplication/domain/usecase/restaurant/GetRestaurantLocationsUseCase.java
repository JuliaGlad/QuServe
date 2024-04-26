package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.location.RestaurantLocationModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetRestaurantLocationsUseCase {
    public Single<List<RestaurantLocationModel>> invoke(String restaurantId) {
        return DI.restaurantRepository.getRestaurantLocations(restaurantId).map(locationDtos ->
                locationDtos.stream()
                        .map(locationDto -> new RestaurantLocationModel(
                                locationDto.getLocationId(),
                                locationDto.getLocation(),
                                locationDto.getCity(),
                                locationDto.getCooksCount(),
                                locationDto.getWaitersCount()
                        ))
                        .collect(Collectors.toList()));
    }
}
