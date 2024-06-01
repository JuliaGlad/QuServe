package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails;

import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.orderIsFinishedCook.CookOrderIsFinishedActivity;

public class OrderDetailsWithIndicatorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_with_indicators);
    }

    public void launchCookOrderIsFinished(ActivityResultLauncher<Intent> launcher, String tableNumber){
        Intent intent = new Intent(this, CookOrderIsFinishedActivity.class);
        intent.putExtra(TABLE_NUMBER, tableNumber);
        launcher.launch(intent);
    }
}