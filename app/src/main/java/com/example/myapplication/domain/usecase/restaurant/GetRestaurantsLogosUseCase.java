package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageTaskModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetRestaurantsLogosUseCase {
    public Single<List<ImageTaskModel>> invoke(){
        return DI.restaurantRepository.getRestaurantLogos().map(imageDtos ->
                imageDtos.stream()
                        .map(ImageTaskModel::new)
                        .collect(Collectors.toList()));
    }
}
