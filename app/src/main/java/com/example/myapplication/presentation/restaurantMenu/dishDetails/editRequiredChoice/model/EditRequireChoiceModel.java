package com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.model;

import java.util.List;

public class EditRequireChoiceModel {
    private final String name;
    private final List<String> variants;

    public EditRequireChoiceModel(String name, List<String> variants) {
        this.name = name;
        this.variants = variants;
    }

    public String getName() {
        return name;
    }

    public List<String> getVariants() {
        return variants;
    }
}
