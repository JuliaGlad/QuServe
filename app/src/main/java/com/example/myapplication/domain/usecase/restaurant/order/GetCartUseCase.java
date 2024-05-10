package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.data.db.entity.CartEntity;
import com.example.myapplication.data.dto.restaurant.CartDishDto;
import com.example.myapplication.data.providers.CartProvider;
import com.example.myapplication.presentation.restaurantOrder.CartDishModel;

import java.util.ArrayList;
import java.util.List;

public class GetCartUseCase {
    public List<CartDishModel> invoke() {
        CartEntity entity = CartProvider.getCart();
        List<CartDishModel> models = new ArrayList<>();
        if (entity != null) {
            for (CartDishDto current : entity.dtos) {
                models.add(new CartDishModel(
                        current.getDishId(),
                        current.getCategoryId(),
                        current.getName(),
                        current.getWeight(),
                        current.getPrice(),
                        current.getAmount(),
                        current.getToppings(),
                        current.getRequiredChoices(),
                        current.getToRemove()
                ));
            }
        }
        return models;
    }
}
