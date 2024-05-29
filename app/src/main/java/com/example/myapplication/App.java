package com.example.myapplication;

import android.app.Application;

import androidx.room.Room;

import com.example.myapplication.data.db.LocalDatabase;

public class App extends Application {

    public static App instance;
    private LocalDatabase database;

        @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, LocalDatabase.class, "database")
                .allowMainThreadQueries().build();

    }


    public static App getInstance() {
        return instance;
    }

    public LocalDatabase getDatabase() {
        return database;
    }
}
