package com.example.myapplication.data.dto.restaurant;

public class DishShortInfoDto {
    private final String name;
    private final String count;

    public DishShortInfoDto(String name, String count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }
}
