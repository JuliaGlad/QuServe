package com.example.myapplication.app;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.data.db.LocalDatabase;

public class App extends Application {

    public static App instance;
    private LocalDatabase database;

    private static final MutableLiveData<AppState> appState = new MutableLiveData<>(new AppState.BasicState());

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, LocalDatabase.class, "database")
                .allowMainThreadQueries().build();

    }

    public static AppState getAppState(){
        return appState.getValue();
    }

    public static void changeState(String state, String companyId){
        switch (state){
            case BASIC:
                appState.postValue(new AppState.BasicState());
                break;
            case COMPANY:
                appState.postValue(new AppState.CompanyState(companyId));
                break;
        }
    }

    public static App getInstance() {
        return instance;
    }

    public LocalDatabase getDatabase() {
        return database;
    }
}
