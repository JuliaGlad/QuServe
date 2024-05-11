package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.recycler;

public class DishTableDetailsItemModel {
    int id;
    String name;
    String count;

    public DishTableDetailsItemModel(int id, String name, String count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public boolean compareTo(DishTableDetailsItemModel other){
        return this.hashCode() == other.hashCode();
    }
}
