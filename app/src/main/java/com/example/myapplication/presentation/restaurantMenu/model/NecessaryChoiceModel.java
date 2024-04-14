package com.example.myapplication.presentation.restaurantMenu.model;

import java.util.List;

public class NecessaryChoiceModel {
    private final String name;
    private final List<VariantsModel> variantNames;

    public NecessaryChoiceModel(String name, List<VariantsModel> variantNames) {
        this.name = name;
        this.variantNames = variantNames;
    }

    public String getName() {
        return name;
    }

    public List<VariantsModel> getVariants() {
        return variantNames;
    }
}
