package com.example.myapplication.data.dto;

public class ToppingDto {
    private final String name;
    private final String price;

    public ToppingDto(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
