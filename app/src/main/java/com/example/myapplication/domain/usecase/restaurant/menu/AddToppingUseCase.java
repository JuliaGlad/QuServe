package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class AddToppingUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String name, String price){
        return DI.restaurantOwnerRepository.addTopping(restaurantId, categoryId, dishId, name, price);
    }
}
