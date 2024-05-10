package com.example.myapplication.domain.usecase.restaurant.restaurantUser;

import com.example.myapplication.data.dto.restaurant.RestaurantDto;
import com.example.myapplication.di.restaurant.RestaurantUserDI;

import io.reactivex.rxjava3.core.Single;

public class GetRestaurantNameByIdsUseCase {
    public Single<String> invoke(String restaurantId){
        return RestaurantUserDI.restaurantUserRepository.getRestaurantByIds(restaurantId)
                .map(RestaurantDto::getName);
    }
}
