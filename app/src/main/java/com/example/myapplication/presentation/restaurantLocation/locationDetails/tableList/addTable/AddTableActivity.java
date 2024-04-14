package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable;

import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddTableBinding;
import com.example.myapplication.databinding.ActivityRestaurantLocationDetailsBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.RestaurantTableDetailsActivity;

public class AddTableActivity extends AppCompatActivity {

    ActivityAddTableBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_add_table_activity);

        setSupportActionBar(binding.toolbar);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbar, navController);
        }

    }

    public void openRestaurantTableDetailsActivity(String tableId, String locationId){
        Intent intent = new Intent(this, RestaurantTableDetailsActivity.class);
        intent.putExtra(LOCATION_ID, locationId);
        intent.putExtra(TABLE_ID, tableId);
        startActivity(intent);
    }
}