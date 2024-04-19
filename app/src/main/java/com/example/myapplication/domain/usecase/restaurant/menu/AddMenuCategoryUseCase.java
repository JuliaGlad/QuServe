package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddMenuCategoryUseCase {
    public Completable invoke(String restaurantId, String categoryId, String categoryName){
        return DI.restaurantOwnerRepository.addMenuCategory(restaurantId, categoryId, categoryName);
    }
}
