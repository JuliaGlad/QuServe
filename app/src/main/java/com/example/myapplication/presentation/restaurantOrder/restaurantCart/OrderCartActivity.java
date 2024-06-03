package com.example.myapplication.presentation.restaurantOrder.restaurantCart;

import static com.example.myapplication.presentation.utils.constants.Restaurant.ORDER_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_PATH;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.orderCreated.OrderCreatedActivity;

public class OrderCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);
    }

    public void openCreatedActivity(ActivityResultLauncher<Intent> launcher, String tablePath, String orderId, String restaurantId){
        Intent intent = new Intent(this, OrderCreatedActivity.class);
        intent.putExtra(TABLE_PATH, tablePath);
        intent.putExtra(ORDER_ID, orderId);
        intent.putExtra(RESTAURANT, restaurantId);
        launcher.launch(intent);
    }
}