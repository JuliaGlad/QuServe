package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetSingleRestaurantLogoUseCase {
    public Single<ImageModel> invoke(String restaurantId){
        return DI.restaurantRepository.getSingleRestaurantLogo(restaurantId).map(imageDto -> new ImageModel(imageDto.getImageUri()));
    }
}
