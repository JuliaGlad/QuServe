package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.restaurant.order.DishShortInfoModel;
import com.example.myapplication.domain.model.restaurant.order.OrderTableDetailsModel;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetOrderByIdsUseCase {
    public Single<OrderTableDetailsModel> invoke(String restaurantId, String locationId, String orderId) {
        return RestaurantOrderDI.restaurantOrderRepository.getOrderByIds(restaurantId, locationId, orderId).map(orderTableDetailsDto ->
                new OrderTableDetailsModel(
                        orderTableDetailsDto.getWaiter(),
                        orderTableDetailsDto.getCook(),
                        orderTableDetailsDto.getDtos().stream().map(dishShortInfoDto -> new DishShortInfoModel(
                                dishShortInfoDto.getName(),
                                dishShortInfoDto.getCount()
                        )).collect(Collectors.toList())
                ));
    }
}
