package com.example.myapplication.presentation.queue.basic.createQueue;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCreateQueueBinding;
import com.example.myapplication.presentation.queue.queueDetails.QueueDetailsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class CreateQueueActivity extends AppCompatActivity {

    private ActivityCreateQueueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateQueueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarCreateQueue);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container_create_queue);

        if (navHostFragment != null) {
            NavController navController = Navigation.findNavController(this, R.id.container_create_queue);

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbarCreateQueue, navController);
        }
    }

    public void openQueueDetailsActivity() {
        Intent intent = new Intent(this, QueueDetailsActivity.class);
        startActivity(intent);
    }
}