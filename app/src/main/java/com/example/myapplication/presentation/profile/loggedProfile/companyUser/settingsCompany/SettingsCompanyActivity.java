package com.example.myapplication.presentation.profile.loggedProfile.companyUser.settingsCompany;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySettingsCompanyBinding;

public class SettingsCompanyActivity extends AppCompatActivity {

    ActivitySettingsCompanyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}