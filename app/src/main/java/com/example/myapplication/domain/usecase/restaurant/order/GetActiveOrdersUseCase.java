package com.example.myapplication.domain.usecase.restaurant.order;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOCATION;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.restaurant.order.ActiveOrderDishModel;
import com.example.myapplication.domain.model.restaurant.order.OrderCookModel;
import com.example.myapplication.domain.model.restaurant.order.OrderShortModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetActiveOrdersUseCase {
    public Single<List<OrderShortModel>> invoke(String restaurantId, String locationId) {
        return RestaurantOrderDI.restaurantOrderRepository.getShortDetailsRestaurantOrders(restaurantId, locationId).map(orderDtos ->
                orderDtos.stream()
                        .filter(orderDto -> orderDto.isOrdered().equals(service.auth.getCurrentUser().getUid()))
                        .map(orderDto -> new OrderShortModel(orderDto.getOrderId(), orderDto.getPath(), orderDto.getTableNumber(), orderDto.getDishesCount()))
                        .collect(Collectors.toList()));
    }
}
