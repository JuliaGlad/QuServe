package com.example.myapplication.presentation.common.JoinQueueFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityJoinQueueBinding;
import com.example.myapplication.presentation.common.waitingInQueue.WaitingActivity;

public class JoinQueueActivity extends AppCompatActivity {

    private ActivityJoinQueueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinQueueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void openWaitingActivity() {
        Intent intent = new Intent(this, WaitingActivity.class);
        startActivity(intent);
    }
}