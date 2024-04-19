package com.example.myapplication.data.dto;

import java.util.List;

public class RequiredChoiceDto {
    private final String name;
    private final List<String> variantsName;

    public RequiredChoiceDto(String name, List<String> variantsName) {
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
