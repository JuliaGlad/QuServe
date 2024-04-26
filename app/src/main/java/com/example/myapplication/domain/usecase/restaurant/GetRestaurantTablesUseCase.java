package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.restaurant.table.RestaurantTableModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetRestaurantTablesUseCase {
    public Single<List<RestaurantTableModel>> invoke(String restaurantId, String locationId){
        return DI.restaurantRepository.getTables(restaurantId, locationId).map(tableDtos ->
                tableDtos.stream()
                        .map(tableDto -> new RestaurantTableModel(tableDto.getTableId(), tableDto.getTableNumber()))
                        .collect(Collectors.toList()));
    }
}
