package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.menu.RequiredChoiceModel;

import io.reactivex.rxjava3.core.Single;

public class GetSingleRequiredChoiceByIdUseCase {
    public Single<RequiredChoiceModel> invoke(String restaurantId, String categoryId, String dishId, String choiceId){
        return DI.restaurantOwnerRepository.getSingleRequireChoiceById(restaurantId, categoryId, dishId, choiceId).map(requiredChoiceDto ->
                new RequiredChoiceModel(
                        requiredChoiceDto.getId(),
                        requiredChoiceDto.getName(),
                        requiredChoiceDto.getVariantsName()
                ));
    }
}
