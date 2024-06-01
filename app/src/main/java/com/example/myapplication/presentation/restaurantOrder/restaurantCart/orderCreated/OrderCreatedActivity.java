package com.example.myapplication.presentation.restaurantOrder.restaurantCart.orderCreated;

import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.presentation.utils.restaurantOrderNotification.NotificationRestaurantForegroundService;

public class OrderCreatedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_created);
    }

    public void startService(String tablePath) {
        Intent intent = new Intent(this, NotificationRestaurantForegroundService.class);
        intent.putExtra(PATH, tablePath);
        startService(intent);
    }
}