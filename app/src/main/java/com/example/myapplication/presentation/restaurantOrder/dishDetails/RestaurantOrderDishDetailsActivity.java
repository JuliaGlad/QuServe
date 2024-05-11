package com.example.myapplication.presentation.restaurantOrder.dishDetails;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_PATH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityRestaurantOrderDishDetailsBinding;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.OrderCartActivity;

public class RestaurantOrderDishDetailsActivity extends AppCompatActivity {

    ActivityRestaurantOrderDishDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantOrderDishDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void openCartActivity(String restaurantId, String tablePath){
        Intent intent = new Intent(this, OrderCartActivity.class);
        intent.putExtra(COMPANY_ID, restaurantId);
        intent.putExtra(TABLE_PATH, tablePath);
        startActivity(intent);
    }
}