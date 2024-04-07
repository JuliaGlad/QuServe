package com.example.myapplication.presentation.service.waitingFragment.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.utils.waitingNotification.NotificationForegroundService;

public class WaitingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
    }

    public void startNotificationForegroundService(){
        Intent intent = new Intent(this, NotificationForegroundService.class);
        startService(intent);
    }

}