package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.restaurant.order.ReadyDishesModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetReadyDishesToWaiterUseCase {
    public Single<List<ReadyDishesModel>> invoke(String restaurantId, String locationId){
        return RestaurantOrderDI.restaurantOrderRepository.getReadyDishesToWaiter(restaurantId, locationId).map(readyDishDtos ->
                readyDishDtos.stream()
                        .map(readyDishDto -> new ReadyDishesModel(
                                readyDishDto.getTableNumber(),
                                readyDishDto.getDishCount(),
                                readyDishDto.getDishName(),
                                readyDishDto.getOrderDocumentId())
                        ).collect(Collectors.toList()));
    }
}
