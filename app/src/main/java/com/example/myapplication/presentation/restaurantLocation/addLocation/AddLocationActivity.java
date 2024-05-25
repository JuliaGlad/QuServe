package com.example.myapplication.presentation.restaurantLocation.addLocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddLocationBinding;
import com.example.myapplication.databinding.ActivityLocationsBinding;

public class AddLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.myapplication.databinding.ActivityAddLocationBinding binding = ActivityAddLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_add_location_activity);

        setSupportActionBar(binding.toolbar);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbar, navController);
        }
    }
}