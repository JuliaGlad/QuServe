package com.example.myapplication.domain.usecase.restaurant.restaurantUser.restaurantImages;

import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetSingleRestaurantLogoUseCase {
    public Single<ImageModel> invoke(String restaurantId){
        return RestaurantUserDI.restaurantImages.getSingleRestaurantLogo(restaurantId).map(
                imageDto -> new ImageModel(imageDto.getImageUri())
        );
    }
}
