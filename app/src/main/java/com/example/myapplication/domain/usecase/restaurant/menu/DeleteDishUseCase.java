package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteDishUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId){
        return RestaurantMenuDI.restaurantMenuRepository.deleteDish(restaurantId, categoryId, dishId);
    }
}
