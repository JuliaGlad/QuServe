package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class AddRequiredChoicesUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String name, List<String> variantsNames){
        return DI.restaurantRepository.addRequiredChoice(restaurantId, categoryId, dishId, choiceId, name, variantsNames);
    }
}
