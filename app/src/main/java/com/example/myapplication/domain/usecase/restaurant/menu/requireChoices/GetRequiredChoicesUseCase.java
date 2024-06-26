package com.example.myapplication.domain.usecase.restaurant.menu.requireChoices;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.domain.model.restaurant.menu.RequiredChoiceModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetRequiredChoicesUseCase {
    public Single<List<RequiredChoiceModel>> invoke(String restaurantId, String categoryId, String dishId){
        return RestaurantMenuDI.requiredChoice.getRequiredChoices(restaurantId, categoryId, dishId).map(requiredChoiceDtos ->
                requiredChoiceDtos.stream()
                        .map(requiredChoiceDto -> new RequiredChoiceModel(requiredChoiceDto.getId(), requiredChoiceDto.getName(), requiredChoiceDto.getVariantsName()))
                        .collect(Collectors.toList()));
    }
}
