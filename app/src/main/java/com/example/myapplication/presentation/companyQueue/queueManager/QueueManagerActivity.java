package com.example.myapplication.presentation.companyQueue.queueManager;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.companyQueue.queueDetails.CompanyQueueDetailsActivity;

public class QueueManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_manager);
    }

    public void openQueueDetails(String companyId, String queueId){
        Intent intent = new Intent(this, CompanyQueueDetailsActivity.class);
        intent.putExtra(COMPANY_ID, companyId);
        intent.putExtra(QUEUE_ID, queueId);
        startActivity(intent);
    }
}