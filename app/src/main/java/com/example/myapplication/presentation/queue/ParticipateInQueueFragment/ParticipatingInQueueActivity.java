package com.example.myapplication.presentation.queue.ParticipateInQueueFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityParticipatinInQueueBinding;

public class ParticipatingInQueueActivity extends AppCompatActivity {

    private ActivityParticipatinInQueueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParticipatinInQueueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarParticipateInQueue);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container_participate_in_queue);

        if (navHostFragment != null) {
            NavController navController = Navigation.findNavController(this, R.id.container_participate_in_queue);

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbarParticipateInQueue, navController);
        }
    }
}