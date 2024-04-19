package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.data.dto.ImageTaskNameDto;
import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageTaskModel;
import com.example.myapplication.domain.model.restaurant.menu.DishMenuOwnerModel;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetDishesImagesUseCase {
    public Single<List<ImageTaskNameModel>> invoke(String restaurantId, List<DishMenuOwnerModel> dishes){
        return DI.restaurantOwnerRepository.getDishesImages(restaurantId, dishes).map(tasks ->
                tasks.stream()
                        .map(task -> new ImageTaskNameModel(task.getTask(), task.getName()))
                        .collect(Collectors.toList()));
    }
}
