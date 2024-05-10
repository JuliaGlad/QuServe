package com.example.myapplication.domain.usecase.restaurant.order;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.restaurant.order.ReadyDishesModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class AddReadyDishesSnapshotUseCase {
    public Observable<ReadyDishesModel> invoke(String restaurantId, String locationId, List<String>  readyDishDocId){
        return RestaurantOrderDI.restaurantOrderRepository.addReadyDishesSnapshot(restaurantId, locationId,  readyDishDocId)
                .map(readyDishDto -> new ReadyDishesModel(
                        readyDishDto.getTableNumber(),
                        readyDishDto.getDishCount(),
                        readyDishDto.getDishName(),
                        readyDishDto.getOrderDocumentId())
                );
    }
}
