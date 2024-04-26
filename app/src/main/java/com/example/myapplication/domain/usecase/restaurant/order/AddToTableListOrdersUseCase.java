package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddToTableListOrdersUseCase {
    public Completable invoke(String path, String orderId){
        return DI.restaurantRepository.addToTableListOrder(path, orderId);
    }
}
