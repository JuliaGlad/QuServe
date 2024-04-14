package com.example.myapplication.presentation.restaurantLocation;

import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLocationsBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.RestaurantLocationDetailsActivity;

public class LocationsActivity extends AppCompatActivity {

    ActivityLocationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_locations_activity);

        setSupportActionBar(binding.toolbar);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbar, navController);
        }
    }

    public void openLocationDetailsActivity(String locationId){
        Intent intent = new Intent(this, RestaurantLocationDetailsActivity.class);
        intent.putExtra(LOCATION_ID, locationId);
        startActivity(intent);
    }
}