package com.example.myapplication.domain.usecase.restaurant.tables;

import com.example.myapplication.di.restaurant.RestaurantTableDI;
import com.example.myapplication.domain.model.restaurant.table.RestaurantTableModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetRestaurantTablesUseCase {
    public Single<List<RestaurantTableModel>> invoke(String restaurantId, String locationId){
        return RestaurantTableDI.restaurantTablesRepository.getTables(restaurantId, locationId).map(tableDtos ->
                tableDtos.stream()
                        .map(tableDto -> new RestaurantTableModel(tableDto.getTableId(), tableDto.getTableNumber(), tableDto.getOrderId()))
                        .collect(Collectors.toList()));
    }
}
