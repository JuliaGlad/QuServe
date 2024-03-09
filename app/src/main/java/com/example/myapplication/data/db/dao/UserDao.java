package com.example.myapplication.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.data.db.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<UserEntity> getUser();

    @Insert
    void insert(UserEntity user);

    @Update
    void update(UserEntity user);

    @Query("DELETE FROM users")
    void deleteALl();

    @Delete
    void delete(UserEntity user);

}