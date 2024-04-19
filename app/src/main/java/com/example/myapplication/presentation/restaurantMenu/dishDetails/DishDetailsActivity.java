package com.example.myapplication.presentation.restaurantMenu.dishDetails;

import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.AddRequiredChoiceActivity;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.AddToppingsActivity;

public class DishDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);
    }

    public void openAddRequiredChoiceActivity(String categoryId, String dishId){
        Intent intent = new Intent(this, AddRequiredChoiceActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        startActivity(intent);
    }

    public void openAddToppingsActivity(String categoryId, String dishId){
        Intent intent = new Intent(this, AddToppingsActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        startActivity(intent);
    }
}