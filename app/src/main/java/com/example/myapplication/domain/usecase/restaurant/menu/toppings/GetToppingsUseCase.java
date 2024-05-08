package com.example.myapplication.domain.usecase.restaurant.menu.toppings;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.domain.model.restaurant.menu.ToppingsModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetToppingsUseCase {
    public Single<List<ToppingsModel>> invoke(String restaurantId, String categoryId, String dishId){
        return RestaurantMenuDI.toppings.getToppings(restaurantId, categoryId, dishId).map(toppingDtos ->
                toppingDtos.stream()
                        .map(toppingDto -> new ToppingsModel(toppingDto.getName(), toppingDto.getPrice()))
                        .collect(Collectors.toList()));
    }
}
