package com.example.myapplication.presentation.restaurantMenu.dishDetails.model;

import java.util.List;

public class RequiredChoiceDishDetailsModel {
    private final String name;
    private final List<String> variantsName;

    public RequiredChoiceDishDetailsModel(String name, List<String> variantsName) {
        this.name = name;
        this.variantsName = variantsName;
    }

    public String getName() {
        return name;
    }

    public List<String> getVariantsName() {
        return variantsName;
    }
}
