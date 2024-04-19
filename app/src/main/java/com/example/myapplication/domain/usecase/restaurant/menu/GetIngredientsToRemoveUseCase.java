package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetIngredientsToRemoveUseCase {
    public Single<List<String>> invoke(String restaurantId, String categoryId, String dishId){
        return DI.restaurantOwnerRepository.getToRemove(restaurantId, categoryId, dishId);
    }

}

