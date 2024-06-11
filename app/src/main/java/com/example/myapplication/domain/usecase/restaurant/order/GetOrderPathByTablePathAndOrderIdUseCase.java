package com.example.myapplication.domain.usecase.restaurant.order;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ACTIVE_ORDERS;

public class GetOrderPathByTablePathAndOrderIdUseCase {
    public String invoke(String tablePath, String orderId){
        return service.fireStore.document(tablePath).getParent().getParent().collection(ACTIVE_ORDERS).document(orderId).getPath();
    }
}
