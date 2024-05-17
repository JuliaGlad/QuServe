package com.example.myapplication.presentation.restaurantLocation.locationDetails;

import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityRestaurantLocationDetailsBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.TableListActivity;

public class RestaurantLocationDetailsActivity extends AppCompatActivity {

    ActivityRestaurantLocationDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantLocationDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
    public void openTableListActivity(String locationId){
        Intent intent = new Intent(this, TableListActivity.class);
        intent.putExtra(LOCATION_ID, locationId);
        startActivity(intent);
    }
}