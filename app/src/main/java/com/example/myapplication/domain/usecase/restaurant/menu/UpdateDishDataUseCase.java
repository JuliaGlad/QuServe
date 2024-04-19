package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateDishDataUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String name, String ingredients, String price, String weightOrCount){
        return DI.restaurantOwnerRepository.updateDishData(restaurantId, categoryId, dishId, name, ingredients, price, weightOrCount);
    }
}
