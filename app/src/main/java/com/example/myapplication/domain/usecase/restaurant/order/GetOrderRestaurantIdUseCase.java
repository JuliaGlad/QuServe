package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.data.providers.CartProvider;

public class GetOrderRestaurantIdUseCase {
    public String invoke(){
        try {
            return CartProvider.getCart().restaurantId;
        } catch (NullPointerException e){
            return null;
        }
    }
}
