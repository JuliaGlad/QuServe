package com.example.myapplication.domain.usecase.restaurant.order;

import android.util.Log;

import com.example.myapplication.data.dto.CartDishDto;
import com.example.myapplication.data.providers.CartProvider;
import com.example.myapplication.presentation.restaurantOrder.CartDishModel;

public class AddDishToCartUseCase {
    public void invoke(String restaurantId, CartDishModel model){
        CartProvider.addToCart(restaurantId, new CartDishDto(
                model.getDishId(),
                model.getCategoryId(),
                model.getName(),
                model.getWeight(),
                model.getPrice(),
                model.getAmount(),
                model.getToppings(),
                model.getRequiredChoices(),
                model.getToRemove()
        ));
    }
}
