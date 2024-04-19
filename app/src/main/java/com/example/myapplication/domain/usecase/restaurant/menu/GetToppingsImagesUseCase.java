package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageTaskModel;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetToppingsImagesUseCase {
    public Single<List<ImageTaskNameModel>> invoke(String restaurantId, String dishId, List<String> names) {
        return DI.restaurantOwnerRepository.getToppingsImages(restaurantId, dishId, names).map(tasks ->
                tasks.stream()
                        .map(task -> new ImageTaskNameModel(task.getTask(), task.getName()))
                        .collect(Collectors.toList()));
    }
}
