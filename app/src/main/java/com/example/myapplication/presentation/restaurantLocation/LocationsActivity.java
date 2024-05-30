package com.example.myapplication.presentation.restaurantLocation;

import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLocationsBinding;
import com.example.myapplication.presentation.restaurantLocation.addLocation.AddLocationActivity;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.RestaurantLocationDetailsActivity;

public class LocationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.myapplication.databinding.ActivityLocationsBinding binding = ActivityLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_locations_activity);

        setSupportActionBar(binding.toolbar);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbar, navController);
        }
    }


    public void openLocationDetailsActivity(String locationId, ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, RestaurantLocationDetailsActivity.class);
        intent.putExtra(LOCATION_ID, locationId);
        launcher.launch(intent);
    }

    public void openAddLocationActivity(ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, AddLocationActivity.class);
        launcher.launch(intent);
    }
}