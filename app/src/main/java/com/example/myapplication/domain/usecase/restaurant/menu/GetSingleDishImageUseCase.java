package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetSingleDishImageUseCase {
    public Single<ImageModel> invoke(String restaurantId, String dishId){
        return DI.restaurantRepository.getSingleDishImage(restaurantId, dishId).map(imageDto ->
                new ImageModel(imageDto.getImageUri()));
    }
}
