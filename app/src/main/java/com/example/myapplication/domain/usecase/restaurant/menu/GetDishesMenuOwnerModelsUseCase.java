package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.menu.DishMenuOwnerModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetDishesMenuOwnerModelsUseCase {
    public Single<List<DishMenuOwnerModel>> invoke(String restaurantId, String categoryId){
        return DI.restaurantRepository.getDishes(restaurantId, categoryId).map(dishDtos ->
                dishDtos.stream()
                        .map(dishDto -> new DishMenuOwnerModel(dishDto.getDishId(),dishDto.getName(),dishDto.getWeightCount(),dishDto.getPrice()))
                        .collect(Collectors.toList()));
    }
}
