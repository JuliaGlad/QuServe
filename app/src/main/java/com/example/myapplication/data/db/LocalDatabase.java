package com.example.myapplication.data.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.data.db.dao.AnonymousUserDao;
import com.example.myapplication.data.db.dao.CartDao;
import com.example.myapplication.data.db.dao.CompanyDao;
import com.example.myapplication.data.db.dao.UserDao;
import com.example.myapplication.data.db.entity.AnonymousUserEntity;
import com.example.myapplication.data.db.entity.CartEntity;
import com.example.myapplication.data.db.entity.CompanyUserEntity;
import com.example.myapplication.data.db.entity.UserEntity;

@Database(entities = {UserEntity.class, CompanyUserEntity.class, CartEntity.class, AnonymousUserEntity.class}, version = 3, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract CompanyDao companyDao();
    public abstract CartDao cartDao();
    public abstract AnonymousUserDao anonymousUserDao();
}
