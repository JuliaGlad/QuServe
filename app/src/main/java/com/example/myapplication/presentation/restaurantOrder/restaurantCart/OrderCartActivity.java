package com.example.myapplication.presentation.restaurantOrder.restaurantCart;

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

    public void openCreatedActivity(){
        Intent intent = new Intent(this, OrderCreatedActivity.class);
        startActivity(intent);
    }
}