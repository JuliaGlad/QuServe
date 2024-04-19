package com.example.myapplication.domain.model.restaurant.menu;

import java.util.List;

public class RequiredChoiceModel {
    private final String name;
    private final List<String> variantsName;

    public RequiredChoiceModel(String name, List<String> variantsName) {
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
