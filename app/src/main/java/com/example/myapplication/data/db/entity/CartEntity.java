package com.example.myapplication.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.data.dto.CartDishDto;

import java.util.List;

@Entity(tableName = "restaurantCart")
public class CartEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public List<CartDishDto> dtos;

    public CartEntity(List<CartDishDto> dtos) {
        this.dtos = dtos;
    }
}
