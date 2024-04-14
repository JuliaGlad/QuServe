package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityRestaurantLocationDetailsBinding;
import com.example.myapplication.databinding.ActivityTableListBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable.AddTableActivity;
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

    public void openAddTableActivity(String restaurantId, String locationId){
        Intent intent = new Intent(this, AddTableActivity.class);
        intent.putExtra(LOCATION_ID, locationId);
        intent.putExtra(COMPANY_ID, restaurantId);
        startActivity(intent);
    }
}