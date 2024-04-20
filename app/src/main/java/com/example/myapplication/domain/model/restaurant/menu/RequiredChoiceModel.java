package com.example.myapplication.domain.model.restaurant.menu;

import java.util.List;

public class RequiredChoiceModel {
    private final String id;
    private final String name;
    private final List<String> variantsName;

    public RequiredChoiceModel(String id, String name, List<String> variantsName) {
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
