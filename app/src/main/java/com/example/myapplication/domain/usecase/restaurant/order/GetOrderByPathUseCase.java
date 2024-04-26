package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.order.ActiveOrderDishModel;
import com.example.myapplication.domain.model.restaurant.order.OrderModel;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetOrderByPathUseCase {
    public Single<OrderModel> invoke(String path) {
        return DI.restaurantRepository.getOrderByPath(path).map(orderDto ->
                new OrderModel(
                        orderDto.getOrderId(),
                        orderDto.getTotalPrice(),
                        orderDto.isOrdered(),
                        orderDto.getDtos().stream().map(dto -> new ActiveOrderDishModel(
                                dto.getDishPath(),
                                dto.getToppings(),
                                dto.getRequiredChoices(),
                                dto.getToRemove()
                        )).collect(Collectors.toList())
                ));
    }
}
