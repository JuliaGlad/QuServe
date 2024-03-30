package com.example.myapplication.presentation.queue.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.app.App;
import com.example.myapplication.app.AppState;

public class ServiceModel extends ViewModel {

    private final MutableLiveData<AppState> _appState = new MutableLiveData<>();
    LiveData<AppState> appState = _appState;

    public void getState(){
        _appState.postValue(App.getAppState());
    }

}