package com.example.myapplication.domain.usecase.restaurant.menu.images;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetCategoriesImagesUseCase {
    public Single<List<ImageTaskNameModel>> invoke(String restaurantId, List<String> categoriesNames) {
        return RestaurantMenuDI.menuImages.getCategoriesImages(restaurantId, categoriesNames).map(tasks ->
                tasks.stream()
                        .map(task -> new ImageTaskNameModel(task.getTask(), task.getName()))
                        .collect(Collectors.toList()));
    }
}
