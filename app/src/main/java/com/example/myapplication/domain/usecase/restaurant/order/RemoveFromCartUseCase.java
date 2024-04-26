package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.data.providers.CartProvider;

public class RemoveFromCartUseCase {
    public void invoke(String dishId){
        CartProvider.removeFromCart(dishId);
    }
}
