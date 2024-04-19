package com.example.myapplication.domain.usecase.restaurant.menu;

import android.util.Log;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.menu.RequiredChoiceModel;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetRequiredChoicesUseCase {
    public Single<List<RequiredChoiceModel>> invoke(String restaurantId, String categoryId, String dishId){
        return DI.restaurantOwnerRepository.getRequiredChoices(restaurantId, categoryId, dishId).map(requiredChoiceDtos ->
                requiredChoiceDtos.stream()
                        .map(requiredChoiceDto -> {
                            Log.e("Variant 0", requiredChoiceDto.getVariantsName().get(0));
                            return new RequiredChoiceModel(requiredChoiceDto.getName(), requiredChoiceDto.getVariantsName());
                        })
                        .collect(Collectors.toList()));
    }
}
