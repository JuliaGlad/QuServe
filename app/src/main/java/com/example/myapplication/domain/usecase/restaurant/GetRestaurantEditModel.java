package com.example.myapplication.domain.usecase.restaurant;

import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.RestaurantEditModel;

import io.reactivex.rxjava3.core.Single;

public class GetRestaurantEditModel {
    public Single<RestaurantEditModel> invoke(String restaurantId) {
        return DI.restaurantOwnerRepository.getRestaurants().map(restaurantDtos ->
                restaurantDtos.stream()
                        .filter(restaurantDto -> restaurantDto.getRestaurantId().equals(restaurantId))
                        .map(restaurantDto -> new RestaurantEditModel(
                                restaurantDto.getName(),
                                restaurantDto.getEmail(),
                                restaurantDto.getPhone(),
                                RESTAURANT))
                        .findFirst()
                        .orElse(null));
    }
}
