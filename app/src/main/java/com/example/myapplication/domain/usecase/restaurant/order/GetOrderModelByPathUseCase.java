package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.restaurant.order.ActiveOrderDishModel;
import com.example.myapplication.domain.model.restaurant.order.OrderModel;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetOrderModelByPathUseCase {
    public Single<OrderModel> invoke(String path){
        return RestaurantOrderDI.restaurantOrderRepository.getOrderByPath(path).map(orderDto ->
                new OrderModel(
                        orderDto.getPath(),
                        orderDto.getOrderId(),
                        orderDto.getRestaurantName(),
                        orderDto.getTableNumber(),
                        orderDto.getTotalPrice(),
                        orderDto.isOrdered(),
                        orderDto.getDtos().stream()
                                .map(activeOrderDishDto -> new ActiveOrderDishModel(
                                        activeOrderDishDto.getToppings(),
                                        activeOrderDishDto.getRequiredChoices(),
                                        activeOrderDishDto.getToRemove()
                                ))
                                .collect(Collectors.toList())
                ));
    }
}
