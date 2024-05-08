package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;

import io.reactivex.rxjava3.core.Completable;

public class TakeOrderByCookUseCase {
    public Completable invoke(String restaurantId, String locationId, String orderId){
        return RestaurantOrderDI.restaurantOrderRepository.takeOrder(restaurantId, locationId, orderId);
    }
}
