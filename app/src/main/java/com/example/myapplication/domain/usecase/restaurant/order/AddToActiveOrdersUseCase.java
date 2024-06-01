package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.model.OrderDishesModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class AddToActiveOrdersUseCase {
    public Single<String> invoke(String restaurantId, String tableId, String path, String orderId, String totalPrice, List<OrderDishesModel> models){
        return RestaurantOrderDI.restaurantOrderRepository.addToActiveOrders(restaurantId, tableId, path, orderId, totalPrice, models);
    }
}
