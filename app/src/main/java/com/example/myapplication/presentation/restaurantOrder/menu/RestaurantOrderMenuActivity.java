package com.example.myapplication.presentation.restaurantOrder.menu;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_PATH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.RestaurantOrderDishDetailsActivity;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.OrderCartActivity;

public class RestaurantOrderMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_order_menu);
    }

    public void openCartActivity(String restaurantId, String tablePath){
        Intent intent = new Intent(this, OrderCartActivity.class);
        intent.putExtra(COMPANY_ID, restaurantId);
        intent.putExtra(TABLE_PATH, tablePath);
        startActivity(intent);
    }

    public void openRestaurantOrderDishDetailsActivity(String restaurantId, String tablePath, String categoryId, String dishId){
        Intent intent = new Intent(this, RestaurantOrderDishDetailsActivity.class);
        intent.putExtra(COMPANY_ID, restaurantId);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        intent.putExtra(TABLE_PATH, tablePath);
        startActivity(intent);
    }
}