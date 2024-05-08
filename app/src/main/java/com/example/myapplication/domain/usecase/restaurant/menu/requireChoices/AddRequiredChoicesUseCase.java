package com.example.myapplication.domain.usecase.restaurant.menu.requireChoices;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class AddRequiredChoicesUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String name, List<String> variantsNames){
        return RestaurantMenuDI.requiredChoice.addRequiredChoice(restaurantId, categoryId, dishId, choiceId, name, variantsNames);
    }
}
