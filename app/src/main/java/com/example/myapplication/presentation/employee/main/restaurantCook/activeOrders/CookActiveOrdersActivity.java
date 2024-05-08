package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders;

import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.common.OrderDetailsActivity;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.dishDetails.OrderDetailsWithIndicatorsActivity;

public class CookActiveOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_active_orders);
    }

    public void openOrderDetailsActivity(String path, String tableNumber){
        Intent intent = new Intent(this, OrderDetailsWithIndicatorsActivity.class);
        intent.putExtra(PATH, path);
        intent.putExtra(TABLE_NUMBER, tableNumber);
        startActivity(intent);
    }
}