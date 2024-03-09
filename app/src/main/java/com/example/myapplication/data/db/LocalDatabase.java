package com.example.myapplication.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.data.db.dao.UserDao;
import com.example.myapplication.data.db.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
