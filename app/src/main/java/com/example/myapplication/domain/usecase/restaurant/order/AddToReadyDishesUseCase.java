package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AddToReadyDishesUseCase {
    public Single<Integer> invoke(String orderDishId, String tableNumber, String dishName, String count, String orderPath){
        return RestaurantOrderDI.restaurantOrderRepository.addToReadyDishes(orderDishId, tableNumber, dishName, count, orderPath);
    }
}
