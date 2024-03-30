package com.example.myapplication.presentation.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.app.App;
import com.example.myapplication.app.AppState;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<AppState> _appState = new MutableLiveData<>();
    LiveData<AppState> appState = _appState;

    public void getAppState() {
        _appState.postValue(App.getAppState());
    }

}