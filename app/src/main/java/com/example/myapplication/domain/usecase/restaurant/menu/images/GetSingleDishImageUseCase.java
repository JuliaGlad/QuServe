package com.example.myapplication.domain.usecase.restaurant.menu.images;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetSingleDishImageUseCase {
    public Single<ImageModel> invoke(String restaurantId, String dishId){
        return RestaurantMenuDI.menuImages.getSingleDishImage(restaurantId, dishId).map(imageDto ->
                new ImageModel(imageDto.getImageUri()));
    }
}
