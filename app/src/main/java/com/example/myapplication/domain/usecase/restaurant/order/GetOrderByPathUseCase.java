package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.restaurant.order.OrderDetailsDishUseCaseModel;
import com.example.myapplication.domain.model.restaurant.order.OrderDetailsModel;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetOrderByPathUseCase {
    public Single<OrderDetailsModel> invoke(String path) {
        return RestaurantOrderDI.restaurantOrderRepository.getOrderByPath(path).map(orderDto ->
                new OrderDetailsModel(
                        orderDto.getOrderId(),
                        orderDto.getRestaurantId(),
                        orderDto.getRestaurantName(),
                        orderDto.getTableNumber(),
                        orderDto.getTotalPrice(),
                        orderDto.getDtos().stream().map(dto -> new OrderDetailsDishUseCaseModel(
                                dto.getDishId(),
                                dto.getDocuDishId(),
                                dto.getAmount(),
                                dto.getName(),
                                dto.getWeight(),
                                dto.getPrice(),
                                dto.getToppings(),
                                dto.getRequiredChoices(),
                                dto.getToRemove()
                        )).collect(Collectors.toList())
                ));
    }
}
