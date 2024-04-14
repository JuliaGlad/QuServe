package com.example.myapplication.presentation.restaurantLocation.locationDetails;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLocationsBinding;
import com.example.myapplication.databinding.ActivityRestaurantLocationDetailsBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.TableListActivity;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable.AddTableActivity;

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