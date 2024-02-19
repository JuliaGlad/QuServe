package com.example.myapplication.presentation.queue.queueDetails;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityQueueDetailsBinding;
import com.example.myapplication.presentation.utils.backToWorkNotification.NotificationGoBackToWork;
import com.example.myapplication.presentation.utils.waitingNotification.NotificationForegroundService;

public class QueueDetailsActivity extends AppCompatActivity {

    private ActivityQueueDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQueueDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarQueueDetails);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container_queue_details);

        if (navHostFragment != null) {
            NavController navController = Navigation.findNavController(this, R.id.container_queue_details);

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbarQueueDetails, navController);
        }
    }

    public void startNotificationForegroundService(){
        Intent intent = new Intent(this, NotificationGoBackToWork.class);
        startService(intent);
    }
}