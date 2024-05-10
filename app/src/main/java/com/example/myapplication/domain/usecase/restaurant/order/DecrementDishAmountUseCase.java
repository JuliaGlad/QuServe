package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.data.dto.restaurant.CartDishDto;
import com.example.myapplication.data.providers.CartProvider;
import com.example.myapplication.presentation.restaurantOrder.CartDishModel;

public class DecrementDishAmountUseCase {
    public void invoke(CartDishModel model){
        CartProvider.decrementDishAmount(new CartDishDto(
                model.getDishId(),
                model.getCategoryId(),
                model.getAmount(),
                model.getName(),
                model.getWeight(),
                model.getPrice(),
                model.getToppings(),
                model.getRequiredChoices(),
                model.getToRemove()
        ));
    }
}
