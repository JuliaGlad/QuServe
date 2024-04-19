package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class AddIngredientsToRemoveUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, List<String> variantsNames){
        return DI.restaurantOwnerRepository.addIngredientsToRemove(restaurantId, categoryId, dishId, variantsNames);
    }
}
