package com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails;

import static com.example.myapplication.presentation.utils.Utils.PROGRESS_MAX;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.STATE;
import static com.example.myapplication.presentation.utils.Utils.TIME_LEFT;
import static com.example.myapplication.presentation.utils.Utils.TIME_MILLIS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCompanyQueueDetailsBinding;
import com.example.myapplication.databinding.ActivityWorkerQueueDetailsBinding;
import com.example.myapplication.presentation.utils.backToWorkNotification.NotificationGoBackToWork;
import com.example.myapplication.presentation.utils.countdownTimer.TimerService;

public class WorkerQueueDetailsActivity extends AppCompatActivity {

    ActivityWorkerQueueDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWorkerQueueDetailsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container_worker_queue_details);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbar, navController);
        }
    }

    public void startNotificationForegroundService(String state){
        Intent intent = new Intent(this, NotificationGoBackToWork.class);
        intent.putExtra(STATE, state);
        startService(intent);
    }

    public void startTimerForegroundService(long time, String timeLeft, String queueId, int progressMax, String type){
        Intent intent = new Intent(this, TimerService.class);
        intent.putExtra(TIME_MILLIS, time);
        intent.putExtra(PROGRESS_MAX, progressMax);
        intent.putExtra(QUEUE_ID, queueId);
        intent.putExtra(TIME_LEFT, timeLeft);
        intent.putExtra(STATE, type);
        startService(intent);
    }

    public void stopServiceForeground(){
        stopService(new Intent(this, TimerService.class));
    }
}