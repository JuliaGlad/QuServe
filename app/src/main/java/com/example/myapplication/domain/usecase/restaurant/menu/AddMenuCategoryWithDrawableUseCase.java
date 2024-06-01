package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class AddMenuCategoryWithDrawableUseCase {
    public Completable invoke(String restaurantId, String categoryId, String categoryName, String image){
        return RestaurantMenuDI.restaurantMenuRepository.addMenuCategoryWithDrawable(restaurantId, categoryId, categoryName, image);
    }
}
