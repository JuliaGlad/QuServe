package com.example.myapplication.presentation.restaurantOrder.restaurantCart.state;

import android.net.Uri;

import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.presentation.restaurantOrder.CartDishModel;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class OrderStateModel {
    private final List<CartDishModel> models;
    private final List<ImageTaskNameModel> uris;

    public OrderStateModel(List<CartDishModel> models, List<ImageTaskNameModel> uris) {
        this.models = models;
        this.uris = uris;
    }

    public List<CartDishModel> getModels() {
        return models;
    }

    public List<ImageTaskNameModel> getUris() {
        return uris;
    }
}
