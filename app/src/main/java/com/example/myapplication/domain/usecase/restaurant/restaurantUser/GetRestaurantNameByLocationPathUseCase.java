package com.example.myapplication.domain.usecase.restaurant.restaurantUser;

import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.example.myapplication.domain.model.restaurant.RestaurantNameIdModel;

import io.reactivex.rxjava3.core.Single;

public class GetRestaurantNameByLocationPathUseCase {
    public Single<RestaurantNameIdModel> invoke(String path){
        return RestaurantUserDI.restaurantUserRepository.getRestaurantByPath(path).map(
                restaurantDto -> new RestaurantNameIdModel(restaurantDto.getName(), restaurantDto.getRestaurantId())
        );
    }
}
