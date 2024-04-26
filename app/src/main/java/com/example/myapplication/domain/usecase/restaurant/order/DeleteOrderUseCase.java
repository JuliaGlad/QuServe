package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.data.providers.CartProvider;

public class DeleteOrderUseCase {
    public void invoke(){
        CartProvider.deleteCart();
    }
}
