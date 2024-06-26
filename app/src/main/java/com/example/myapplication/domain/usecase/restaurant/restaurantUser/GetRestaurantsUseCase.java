package com.example.myapplication.domain.usecase.restaurant.restaurantUser;

import static com.example.myapplication.di.DI.service;

import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.example.myapplication.domain.model.restaurant.RestaurantNameIdModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetRestaurantsUseCase {
    public Single<List<RestaurantNameIdModel>> invoke(){
        return RestaurantUserDI.restaurantUserRepository.getRestaurants().map(restaurantDtos ->
                restaurantDtos
                        .stream()
                        .filter(restaurantDto -> restaurantDto.getOwner().equals(service.auth.getCurrentUser().getUid()))
                        .map(restaurantDto -> new RestaurantNameIdModel(restaurantDto.getName(), restaurantDto.getRestaurantId()))
                        .collect(Collectors.toList()));
    }
}
