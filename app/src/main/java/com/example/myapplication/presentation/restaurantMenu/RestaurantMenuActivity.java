package com.example.myapplication.presentation.restaurantMenu;

import static com.example.myapplication.presentation.utils.constants.Utils.POSITION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.DishDetailsActivity;

public class RestaurantMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
    }

    public void openDishDetailsActivity(String categoryId, String dishId, ActivityResultLauncher<Intent> launcher, int index){
        Intent intent = new Intent(this, DishDetailsActivity.class);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        intent.putExtra(POSITION, index);
        launcher.launch(intent);
    }
}