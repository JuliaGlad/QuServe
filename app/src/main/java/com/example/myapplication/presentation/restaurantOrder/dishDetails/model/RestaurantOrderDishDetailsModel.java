package com.example.myapplication.presentation.restaurantOrder.dishDetails.model;

import android.net.Uri;

import com.example.myapplication.presentation.restaurantMenu.dishDetails.model.RequiredChoiceDishDetailsModel;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;

import java.util.List;

public class RestaurantOrderDishDetailsModel {
    private final String name;
    private final String price;
    private final String estimatedTime;
    private final String ingredients;
    private final String weightOrCount;
    private final List<String> ingredientsToRemove;
    private final List<VariantsModel> toppings;
    private final List<RequiredChoiceOrderDishDetailsModel> models;
    private final Uri uri;

    public RestaurantOrderDishDetailsModel(String name, String price, String estimatedTime, String ingredients, String weightOrCount, List<String> ingredientsToRemove, List<VariantsModel> toppings, List<RequiredChoiceOrderDishDetailsModel> models, Uri uri) {
        this.name = name;
        this.price = price;
        this.estimatedTime = estimatedTime;
        this.ingredients = ingredients;
        this.weightOrCount = weightOrCount;
        this.ingredientsToRemove = ingredientsToRemove;
        this.toppings = toppings;
        this.models = models;
        this.uri = uri;
    }

    public List<RequiredChoiceOrderDishDetailsModel> getModels() {
        return models;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getWeightOrCount() {
        return weightOrCount;
    }

    public List<String> getIngredientsToRemove() {
        return ingredientsToRemove;
    }

    public List<VariantsModel> getToppings() {
        return toppings;
    }
}
