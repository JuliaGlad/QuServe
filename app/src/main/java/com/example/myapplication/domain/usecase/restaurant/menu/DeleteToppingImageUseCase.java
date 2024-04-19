package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteToppingImageUseCase {
    public Completable invoke(String restaurantId, String dishId, String name){
        return DI.restaurantOwnerRepository.deleteToppingImage(restaurantId, dishId, name);
    }
}
