package com.example.myapplication.data.dto;

import java.util.List;

public class RequiredChoiceDto {
    private final String id;
    private final String name;
    private final List<String> variantsName;

    public RequiredChoiceDto(String id, String name, List<String> variantsName) {
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
