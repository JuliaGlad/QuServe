package com.example.myapplication.domain.usecase.restaurant.tables;

import com.example.myapplication.di.restaurant.RestaurantTableDI;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.model.TableModel;

import io.reactivex.rxjava3.core.Single;

public class GetSingleTableByIdUseCase {
    public Single<TableModel> invoke(String restaurantId, String locationId, String tableId) {
        return RestaurantTableDI.restaurantTablesRepository.getSingleTableById(restaurantId, locationId, tableId).map(tableDto ->
                new TableModel(
                        tableDto.getTableNumber(),
                        tableDto.getTableId(),
                        tableDto.getOrderId()
                ));
    }
}
