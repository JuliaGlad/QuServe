package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;

import io.reactivex.rxjava3.core.Completable;

public class FinishOrderByPathUseCase {
    public Completable invoke(String orderPath){
        return RestaurantOrderDI.restaurantOrderRepository.finishOrder(orderPath);
    }
}
