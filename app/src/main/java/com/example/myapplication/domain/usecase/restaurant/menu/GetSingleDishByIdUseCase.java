package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.menu.DishDetailsModel;

import io.reactivex.rxjava3.core.Single;

public class GetSingleDishByIdUseCase {
    public Single<DishDetailsModel> invoke(String restaurantId, String categoryId, String dishId){
        return DI.restaurantRepository.getSingleDishById(restaurantId, categoryId, dishId).map(dishDto ->
                new DishDetailsModel(
                        dishId,
                        dishDto.getName(),
                        dishDto.getWeightCount(),
                        dishDto.getPrice(),
                        dishDto.getEstimatedTimeCooking(),
                        dishDto.getIngredients(),
                        dishDto.getToRemove()));
    }
}
