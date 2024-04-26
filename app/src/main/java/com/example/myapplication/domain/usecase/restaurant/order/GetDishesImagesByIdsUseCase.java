package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetDishesImagesByIdsUseCase {
    public Single<List<ImageTaskNameModel>> invoke(String restaurantId, List<String> ids){
        return DI.restaurantRepository.getDishesImagesByIds(restaurantId, ids).map(imageTaskNameDtos ->
                imageTaskNameDtos.stream()
                        .map(imageTaskNameDto -> new ImageTaskNameModel(imageTaskNameDto.getTask(), imageTaskNameDto.getName()))
                        .collect(Collectors.toList()));
    }
}
