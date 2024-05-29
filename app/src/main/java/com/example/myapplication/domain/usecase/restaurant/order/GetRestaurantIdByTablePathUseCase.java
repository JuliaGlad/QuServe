package com.example.myapplication.domain.usecase.restaurant.order;

import static com.example.myapplication.di.DI.service;

public class GetRestaurantIdByTablePathUseCase {
    public String invoke(String tablePath){
        return service.fireStore.document(tablePath).getParent().getParent().getParent().getParent().getId();
    }
}
