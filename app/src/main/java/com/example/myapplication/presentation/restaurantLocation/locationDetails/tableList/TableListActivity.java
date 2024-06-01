package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList;

import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityTableListBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.RestaurantTableDetailsActivity;

public class TableListActivity extends AppCompatActivity {
    
    ActivityTableListBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityTableListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void openRestaurantTableDetailsActivity(String tableId, String locationId){
        Intent intent = new Intent(this, RestaurantTableDetailsActivity.class);
        intent.putExtra(LOCATION_ID, locationId);
        intent.putExtra(TABLE_ID, tableId);
        startActivity(intent);
    }
}