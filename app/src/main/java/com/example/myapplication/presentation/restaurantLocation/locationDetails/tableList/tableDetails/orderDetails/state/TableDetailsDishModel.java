package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.state;

public class TableDetailsDishModel {
    private final String name;
    private final String count;

    public TableDetailsDishModel(String name, String count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }
}
