package com.example.myapplication.data.dto;

import java.util.List;

public class CartDto {
    private final List<CartDishDto> dtos;

    public CartDto(List<CartDishDto> dtos) {
        this.dtos = dtos;
    }

    public List<CartDishDto> getDtos() {
        return dtos;
    }
}
