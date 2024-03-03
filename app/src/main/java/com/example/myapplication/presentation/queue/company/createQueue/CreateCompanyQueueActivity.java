package com.example.myapplication.presentation.queue.company.createQueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.queue.company.createQueue.map.MapActivity;

public class CreateCompanyQueueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_company_queue);
    }

    public void openMap(){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}