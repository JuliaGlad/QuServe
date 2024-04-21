package com.example.myapplication.presentation.restaurantMenu.dishDetails;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityDishDetailsBinding;

public class DishDetailsActivity extends AppCompatActivity {

    ActivityDishDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDishDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_dish_details);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbar, navController);
        }
    }

}