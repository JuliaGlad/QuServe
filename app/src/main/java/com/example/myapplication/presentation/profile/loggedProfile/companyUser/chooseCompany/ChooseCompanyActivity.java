package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.STATE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.companyQueue.createQueue.CreateCompanyQueueActivity;
import com.example.myapplication.presentation.companyQueue.queueManager.QueueManagerActivity;
import com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.CreateCompanyActivity;

public class ChooseCompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_company);
    }

    public void openCreateCompanyQueueActivity(String companyId){
        Intent intent = new Intent(this, CreateCompanyQueueActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(COMPANY_ID, companyId);
        startActivity(intent);
    }

    public void openQueueManagerActivity(String companyId){
        Intent intent = new Intent(this, QueueManagerActivity.class);
        intent.putExtra(COMPANY_ID, companyId);
        startActivity(intent);
    }

    public void openCreateCompanyActivity(){
        Intent intent = new Intent(this, CreateCompanyActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        startActivity(intent);
    }

}