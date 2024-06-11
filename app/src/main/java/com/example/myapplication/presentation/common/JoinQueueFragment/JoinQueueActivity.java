package com.example.myapplication.presentation.common.JoinQueueFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityJoinQueueBinding;
import com.example.myapplication.presentation.common.waitingInQueue.WaitingActivity;

public class JoinQueueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.myapplication.databinding.ActivityJoinQueueBinding binding = ActivityJoinQueueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void openWaitingActivity(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(this, WaitingActivity.class);
        launcher.launch(intent);
    }
}