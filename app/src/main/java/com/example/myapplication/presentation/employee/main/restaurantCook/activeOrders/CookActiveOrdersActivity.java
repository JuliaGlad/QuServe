package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders;

import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCookActiveOrdersBinding;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.OrderDetailsWithIndicatorsActivity;

public class CookActiveOrdersActivity extends AppCompatActivity {

    private ActivityCookActiveOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCookActiveOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController);
            NavigationUI.setupWithNavController(binding.toolbar, navController);
        }

    }

    public void openOrderDetailsActivity(String path, String tableNumber, ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, OrderDetailsWithIndicatorsActivity.class);
        intent.putExtra(PATH, path);
        intent.putExtra(TABLE_NUMBER, tableNumber);
        launcher.launch(intent);
    }
}