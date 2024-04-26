package com.example.myapplication.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.data.db.entity.CartEntity;

@Dao
public interface CartDao {

    @Query("SELECT * FROM restaurantCart")
    CartEntity getCart();

    @Insert
    void insert(CartEntity cart);

    @Update
    void update(CartEntity cart);

    @Delete
    void delete(CartEntity cart);

}
