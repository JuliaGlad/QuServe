package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.state;

import java.util.List;

public class TableOrderDetailsStateModel {
    private final String cookName;
    private final String waiterName;
    private final List<TableDetailsDishModel> dishes;

    public TableOrderDetailsStateModel(String cookName, String waiterName, List<TableDetailsDishModel> dishes) {
        this.cookName = cookName;
        this.waiterName = waiterName;
        this.dishes = dishes;
    }

    public String getCookName() {
        return cookName;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public List<TableDetailsDishModel> getDishes() {
        return dishes;
    }
}
