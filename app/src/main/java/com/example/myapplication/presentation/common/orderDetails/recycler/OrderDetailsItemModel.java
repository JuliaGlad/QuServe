package com.example.myapplication.presentation.common.orderDetails.recycler;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import java.util.List;

public class OrderDetailsItemModel {
    int id;
    String name;
    String weight;
    String amount;
    String totalPrice;
    Task<Uri> task;
    List<String> requiredChoice;
    List<String> toppings;
    List<String> toRemove;

    public OrderDetailsItemModel(int id, String name, String totalPrice, String weight, String amount, Task<Uri> task, List<String> requiredChoice, List<String> toppings, List<String> toRemove) {
        this.id = id;
        this.name = name;
        this.task = task;
        this.totalPrice = totalPrice;
        this.weight = weight;
        this.amount = amount;
        this.requiredChoice = requiredChoice;
        this.toppings = toppings;
        this.toRemove = toRemove;
    }

    public boolean compareTo(OrderDetailsItemModel other){
        return this.hashCode() == other.hashCode();
    }
}
