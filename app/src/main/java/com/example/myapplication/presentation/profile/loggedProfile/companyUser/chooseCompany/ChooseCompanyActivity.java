package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany;

import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.databinding.ActivityChooseCompanyBinding;
import com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.CreateCompanyActivity;

public class ChooseCompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChooseCompanyBinding binding = ActivityChooseCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void openCreateCompanyActivity(){
        Intent intent = new Intent(this, CreateCompanyActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        startActivity(intent);
    }

}