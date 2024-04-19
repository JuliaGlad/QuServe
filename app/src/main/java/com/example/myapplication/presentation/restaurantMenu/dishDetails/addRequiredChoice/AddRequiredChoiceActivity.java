package com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddRequiredChoiceBinding;
import com.example.myapplication.databinding.ActivityQueueDetailsBinding;

public class AddRequiredChoiceActivity extends AppCompatActivity {

    ActivityAddRequiredChoiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRequiredChoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container_queue_details);

        if (navHostFragment != null) {
            NavController navController = Navigation.findNavController(this, R.id.container_queue_details);

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbar, navController);
        }
    }
}