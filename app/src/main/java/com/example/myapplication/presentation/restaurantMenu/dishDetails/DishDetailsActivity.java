package com.example.myapplication.presentation.restaurantMenu.dishDetails;

import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityDishDetailsBinding;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.AddRequiredChoiceActivity;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.AddToppingsActivity;

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

    public void openAddRequiredChoiceActivity(String categoryId, String dishId){
        Intent intent = new Intent(this, AddRequiredChoiceActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        startActivity(intent);
    }

    public void openAddToppingsActivity(String categoryId, String dishId){
        Intent intent = new Intent(this, AddToppingsActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        startActivity(intent);
    }
}