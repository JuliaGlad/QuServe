package com.example.myapplication.presentation.companyQueue.createQueue;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_ID;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.companyQueue.queueDetails.CompanyQueueDetailsActivity;

public class CreateCompanyQueueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_company_queue);
    }

    public void openCompanyQueueDetailsActivity(String companyId, String queueId, ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(this, CompanyQueueDetailsActivity.class);
        intent.putExtra(COMPANY_ID, companyId);
        intent.putExtra(QUEUE_ID, queueId);
        launcher.launch(intent);
    }

}