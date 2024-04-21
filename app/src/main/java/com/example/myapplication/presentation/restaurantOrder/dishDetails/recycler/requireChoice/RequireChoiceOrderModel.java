package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requireChoice;

public class RequireChoiceOrderModel {
    int id;
    String name;
    boolean isDefault;
    String chosen;

    public RequireChoiceOrderModel(int id, String name, boolean isDefault, String chosen) {
        this.id = id;
        this.name = name;
        this.isDefault = isDefault;
        this.chosen = chosen;
    }

    public boolean compareTo(RequireChoiceOrderModel other){
        return this.hashCode() == other.hashCode();
    }
}
