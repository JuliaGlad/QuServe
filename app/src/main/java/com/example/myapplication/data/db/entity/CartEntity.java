package com.example.myapplication.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.myapplication.data.dto.restaurant.CartDishDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "restaurantCart")
public class CartEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String restaurantId;

    @TypeConverters({CartDishConverters.class})
    public List<CartDishDto> dtos;

    public CartEntity(String restaurantId, List<CartDishDto> dtos) {
        this.restaurantId = restaurantId;
        this.dtos = dtos;
    }

    public static class CartDishConverters {
        @TypeConverter
        public static List<CartDishDto> toCartDishDto(String value) {
            Type listType = new TypeToken<ArrayList<CartDishDto>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }

        @TypeConverter
        public static String fromCartDishDto(List<CartDishDto> list) {
            Gson gson = new Gson();
            return gson.toJson(list);
        }
    }
}
