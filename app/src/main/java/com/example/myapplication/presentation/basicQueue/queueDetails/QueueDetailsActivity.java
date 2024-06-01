package com.example.myapplication.presentation.basicQueue.queueDetails;

import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.TIME_LEFT;
import static com.example.myapplication.presentation.utils.constants.Utils.TIME_MILLIS;

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
import com.example.myapplication.presentation.utils.countdownTimer.TimerService;

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


    public void startNotificationForegroundService(String type){
        Intent intent = new Intent(this, NotificationGoBackToWork.class);
        intent.putExtra(STATE, type);
        startService(intent);
    }

    public void startTimerForegroundService(long time, String timeLeft, String queueId, String type){
        Intent intent = new Intent(this, TimerService.class);
        intent.putExtra(TIME_MILLIS, time);
        intent.putExtra(QUEUE_ID, queueId);
        intent.putExtra(TIME_LEFT, timeLeft);
        intent.putExtra(STATE, type);
        startService(intent);
    }

    public void stopServiceForeground(){
        stopService(new Intent(this, TimerService.class));
    }

}