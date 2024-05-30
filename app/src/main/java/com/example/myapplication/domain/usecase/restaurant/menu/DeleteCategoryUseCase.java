package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteCategoryUseCase {
    public Completable invoke(String restaurantId, String categoryId){
        return RestaurantMenuDI.restaurantMenuRepository.deleteCategory(restaurantId, categoryId);
    }
}
