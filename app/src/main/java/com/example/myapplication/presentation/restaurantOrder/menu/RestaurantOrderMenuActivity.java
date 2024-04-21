package com.example.myapplication.presentation.restaurantOrder.menu;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.RestaurantOrderDishDetailsActivity;

public class RestaurantOrderMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_order_menu);
    }

    public void openRestaurantOrderDishDetailsActivity(String restaurantId, String categoryId, String dishId){
        Intent intent = new Intent(this, RestaurantOrderDishDetailsActivity.class);
        intent.putExtra(COMPANY_ID, restaurantId);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        startActivity(intent);
    }
}