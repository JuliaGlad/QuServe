package com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddToppingsBinding;

public class AddToppingsActivity extends AppCompatActivity {

    ActivityAddToppingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddToppingsBinding.inflate(getLayoutInflater());
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