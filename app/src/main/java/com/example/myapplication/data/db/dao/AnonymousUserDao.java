package com.example.myapplication.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.data.db.entity.AnonymousUserEntity;

@Dao
public interface AnonymousUserDao {

    @Query("SELECT * FROM anonymousUser")
    AnonymousUserEntity getUser();

    @Insert
    void insert(AnonymousUserEntity entity);

    @Update
    void update(AnonymousUserEntity entity);

    @Delete
    void delete(AnonymousUserEntity entity);

}
