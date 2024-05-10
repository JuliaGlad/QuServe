package com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders;

import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.common.orderDetails.OrderDetailsActivity;

public class AvailableCookOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cook_orders);
    }

    public void openOrderDetailsActivity(String path){
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra(PATH, path);
        startActivity(intent);
    }
}