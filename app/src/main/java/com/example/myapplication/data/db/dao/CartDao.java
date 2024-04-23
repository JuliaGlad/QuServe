package com.example.myapplication.data.db.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.data.db.entity.CartEntity;

public interface CartDao {

    @Query("SELECT * FROM restaurantCart")
    CartEntity getCart();

    @Insert
    void insert(CartEntity cart);

    @Update
    void update(CartEntity cart);

    @Delete
    void delete(String dishId);

}
