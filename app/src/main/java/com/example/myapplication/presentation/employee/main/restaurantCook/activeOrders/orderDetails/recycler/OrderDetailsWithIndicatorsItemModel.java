package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.recycler;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import java.util.List;

import myapplication.android.ui.listeners.ButtonItemListener;

public class OrderDetailsWithIndicatorsItemModel {
    int id;
    String name;
    String weight;
    String amount;
    String totalPrice;
    Task<Uri> task;
    List<String> requiredChoice;
    List<String> toppings;
    List<String> toRemove;
    ButtonItemListener listener;

    public OrderDetailsWithIndicatorsItemModel(int id, String name, String totalPrice, String weight, String amount, Task<Uri> task, List<String> requiredChoice, List<String> toppings, List<String> toRemove, ButtonItemListener listener) {
        this.id = id;
        this.listener = listener;
        this.name = name;
        this.task = task;
        this.totalPrice = totalPrice;
        this.weight = weight;
        this.amount = amount;
        this.requiredChoice = requiredChoice;
        this.toppings = toppings;
        this.toRemove = toRemove;
    }


    public int getId() {
        return id;
    }

    public boolean compareTo(OrderDetailsWithIndicatorsItemModel other){
        return this.hashCode() == other.hashCode();
    }
}
