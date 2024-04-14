package com.example.myapplication.data.dto;

import com.example.myapplication.presentation.restaurantMenu.model.NecessaryChoiceModel;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;

import java.util.List;

public class DishDto {

    String categoryName;
    String dishId;
    String name;
    List<String> ingredients;
    String weightCount;
    String price;
    String estimatedTimeCooking;
    List<NecessaryChoiceDto> necessaryChoices;
    List<String> toRemove;
    List<String> toppings;

    public DishDto(
            String dishId,
            String name,
            List<String> ingredients,
            String weightCount,
            String price,
            String estimatedTimeCooking,
            List<NecessaryChoiceDto> necessaryChoices,
            List<String> toRemove,
            List<String> toppings
    ) {
        this.dishId = dishId;
        this.name = name;
        this.ingredients = ingredients;
        this.weightCount = weightCount;
        this.price = price;
        this.estimatedTimeCooking = estimatedTimeCooking;
        this.necessaryChoices = necessaryChoices;
        this.toRemove = toRemove;
        this.toppings = toppings;
    }

    public String getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getWeightCount() {
        return weightCount;
    }

    public String getPrice() {
        return price;
    }

    public String getEstimatedTimeCooking() {
        return estimatedTimeCooking;
    }

    public List<NecessaryChoiceDto> getNecessaryChoices() {
        return necessaryChoices;
    }

    public List<String> getToRemove() {
        return toRemove;
    }

    public List<String> getToppings() {
        return toppings;
    }
}
