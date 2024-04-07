package com.example.myapplication.presentation.service.main.basicUser.becomeEmployeeOptions;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.myapplication.databinding.ActivityBecomeEmployeeOptionsBinding;

public class BecomeEmployeeOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityBecomeEmployeeOptionsBinding binding = ActivityBecomeEmployeeOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}