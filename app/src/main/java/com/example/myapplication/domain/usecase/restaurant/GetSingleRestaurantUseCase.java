package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.RestaurantEmailNameModel;

import io.reactivex.rxjava3.core.Single;

public class GetSingleRestaurantUseCase {
    public Single<RestaurantEmailNameModel> invoke(String restaurantId){
        return DI.restaurantRepository.getRestaurants().map(restaurantDtos ->
                restaurantDtos.stream()
                        .filter(restaurantDto -> restaurantDto.getRestaurantId().equals(restaurantId))
                        .map(restaurantDto -> new RestaurantEmailNameModel(restaurantId, restaurantDto.getName(), restaurantDto.getEmail() ))
                        .findFirst()
                        .orElse(null));
    }
}
