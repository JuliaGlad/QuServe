package com.example.myapplication.presentation.home.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.databinding.ActivityQuServeFeaturesBinding;

public class StoriesActivity extends AppCompatActivity {

    ActivityQuServeFeaturesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityQuServeFeaturesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}