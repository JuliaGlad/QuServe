package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AddOrderToHistoryUseCase {
    public Completable invoke(String orderId, String name, String timeLeft, String date){
        return RestaurantOrderDI.restaurantOrderRepository.addOrderToHistory(orderId, name, timeLeft, date);
    }
}
