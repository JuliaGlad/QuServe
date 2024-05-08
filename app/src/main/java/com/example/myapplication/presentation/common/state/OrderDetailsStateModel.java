package com.example.myapplication.presentation.common.state;

import com.example.myapplication.domain.model.restaurant.order.ActiveOrderDishModel;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class OrderDetailsStateModel {
    private final String orderId;
    private final String totalPrice;
    private final List<OrderDetailsDishModel> models;

    public OrderDetailsStateModel(String orderId,  String totalPrice, List<OrderDetailsDishModel> models) {
        this.models = models;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public List<OrderDetailsDishModel> getModels() {
        return models;
    }
}
