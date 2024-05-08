package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.domain.model.restaurant.menu.CategoryModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetCategoriesUseCase {
    public Single<List<CategoryModel>> invoke(String restaurantId) {
        return RestaurantMenuDI.restaurantMenuRepository.getCategories(restaurantId).map(categoryDtos ->
                categoryDtos.stream()
                        .map(categoryDto -> new CategoryModel(categoryDto.getCategoryId(), categoryDto.getName()))
                        .collect(Collectors.toList()));
    }
}
