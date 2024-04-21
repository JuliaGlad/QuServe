package com.example.myapplication.presentation.restaurantOrder.dishDetails.model;

import java.util.List;

public class RequiredChoiceOrderDishDetailsModel {
    private final String id;
    private final String name;
    private final List<String> variantsName;

    public RequiredChoiceOrderDishDetailsModel(String id, String name, List<String> variantsName) {
        this.id = id;
        this.name = name;
        this.variantsName = variantsName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getVariantsName() {
        return variantsName;
    }
}
