package com.example.myapplication.presentation.restaurantMenu;

import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.AddMenuCategoryActivity;
import com.example.myapplication.presentation.restaurantMenu.addDish.AddDishActivity;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.DishDetailsActivity;

public class RestaurantMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
    }

    public void openDishDetailsActivity(String categoryId, String dishId){
        Intent intent = new Intent(this, DishDetailsActivity.class);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        startActivity(intent);
    }

    public void openCategoryActivity(){
        Intent intent = new Intent(this, AddMenuCategoryActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        startActivity(intent);
    }

    public void openAddDishActivity(String categoryId){
        Intent intent = new Intent(this, AddDishActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(CATEGORY_ID, categoryId);
        startActivity(intent);
    }
}