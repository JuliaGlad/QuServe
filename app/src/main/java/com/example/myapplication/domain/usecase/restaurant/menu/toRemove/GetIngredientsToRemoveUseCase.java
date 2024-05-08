package com.example.myapplication.domain.usecase.restaurant.menu.toRemove;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetIngredientsToRemoveUseCase {
    public Single<List<String>> invoke(String restaurantId, String categoryId, String dishId){
        return RestaurantMenuDI.toRemove.getToRemove(restaurantId, categoryId, dishId);
    }

}

