package com.example.myapplication.presentation.restaurantMenu.addDish;

import static com.example.myapplication.presentation.utils.constants.Utils.HOURS;

import android.net.Uri;
import android.os.Bundle;

class DishArguments {
    static Bundle bundle = new Bundle();
    static String name, ingredients, weightCount, price, timeCooking;
    static Uri imageUri = Uri.EMPTY;
}
