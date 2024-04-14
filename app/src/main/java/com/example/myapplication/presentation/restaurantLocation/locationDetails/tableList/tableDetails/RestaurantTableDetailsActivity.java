package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityRestaurantTableDetailsBinding;

public class RestaurantTableDetailsActivity extends AppCompatActivity {

    ActivityRestaurantTableDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_table_details);

        binding = ActivityRestaurantTableDetailsBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_table_details_activity);

        setSupportActionBar(binding.toolbar);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbar, navController);
        }
    }
}