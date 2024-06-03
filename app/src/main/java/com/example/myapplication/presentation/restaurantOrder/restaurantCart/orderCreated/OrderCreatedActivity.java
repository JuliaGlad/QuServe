package com.example.myapplication.presentation.restaurantOrder.restaurantCart.orderCreated;

import static com.example.myapplication.presentation.utils.constants.Restaurant.ORDER_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

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

    public void startService(String tablePath, String orderId, String restaurantId) {
        Intent intent = new Intent(this, NotificationRestaurantForegroundService.class);
        intent.putExtra(PATH, tablePath);
        intent.putExtra(ORDER_ID, orderId);
        intent.putExtra(RESTAURANT, restaurantId);
        startService(intent);
    }
}