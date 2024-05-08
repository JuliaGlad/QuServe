package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;

import io.reactivex.rxjava3.core.Completable;

public class AddToTableListOrdersUseCase {
    public Completable invoke(String path, String orderId){
        return RestaurantOrderDI.restaurantOrderRepository.addToTableListOrder(path, orderId);
    }
}
