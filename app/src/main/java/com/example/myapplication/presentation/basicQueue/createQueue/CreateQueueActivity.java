package com.example.myapplication.presentation.basicQueue.createQueue;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCreateQueueBinding;
import com.example.myapplication.presentation.basicQueue.queueDetails.QueueDetailsActivity;
import com.example.myapplication.presentation.companyQueue.queueDetails.CompanyQueueDetailsActivity;

public class CreateQueueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.myapplication.databinding.ActivityCreateQueueBinding binding = ActivityCreateQueueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarCreateQueue);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container_create_queue);

        if (navHostFragment != null) {
            NavController navController = Navigation.findNavController(this, R.id.container_create_queue);

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbarCreateQueue, navController);
        }
    }

    public void openQueueDetailsActivity(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(this, QueueDetailsActivity.class);
        launcher.launch(intent);
    }
}