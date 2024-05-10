package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;

import io.reactivex.rxjava3.core.Completable;

public class OnDishServedUseCase {
    public Completable invoke(String restaurantId, String locationId, String readyDishDocId){
        return RestaurantOrderDI.restaurantOrderRepository.dishServed(restaurantId, locationId, readyDishDocId);
    }
}
