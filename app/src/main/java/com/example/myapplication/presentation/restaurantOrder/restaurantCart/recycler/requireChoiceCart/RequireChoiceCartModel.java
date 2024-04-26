package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.requireChoiceCart;

public class RequireChoiceCartModel {
    int id;
    String name;

    public RequireChoiceCartModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean compareTo(RequireChoiceCartModel other){
        return other.hashCode() == this.hashCode();
    }
}
