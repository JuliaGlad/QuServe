package com.example.myapplication.domain.usecase.restaurant.order;

import static com.example.myapplication.di.DI.service;

public class GetRestaurantIdByTablePathUseCase {
    public String invoke(String tablePath){
        try {
            return service.fireStore.document(tablePath).getParent().getParent().getParent().getParent().getId();
        } catch (IllegalArgumentException | NullPointerException e){
            return null;
        }
    }
}
