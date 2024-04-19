package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;
import com.example.myapplication.presentation.restaurantMenu.model.NecessaryChoiceModel;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class AddDishUseCase {
    public Completable invoke(
            String restaurantId,
            String categoryId,
            String dishId,
            String name,
            String ingredients,
            String weightCount,
            String price,
            String estimatedTimeCooking){

        return DI.restaurantOwnerRepository.addCompanyDish(restaurantId, categoryId, dishId, name, ingredients, weightCount, price, estimatedTimeCooking);

    }
 }
