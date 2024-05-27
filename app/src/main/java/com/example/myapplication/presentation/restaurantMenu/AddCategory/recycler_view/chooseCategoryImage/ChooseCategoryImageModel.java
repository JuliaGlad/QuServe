package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.chooseCategoryImage;


import android.net.Uri;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ChooseCategoryImageModel {
    int id;
    List<Integer> drawables;
    String name;
    Uri uri;
    List<String> drawablesIds = Arrays.asList(
            "beer_image",
            "desserts_image",
            "garnish_image",
            "juice_image",
            "pasta_image",
            "pizza_image",
            "salad_image",
            "sauce_image",
            "soup_image",
            "sushi_image",
            "wok_noodles_image",
            "cold_dishes_image",
            "flour_products_image",
            "fast_food_image"
    );
    ButtonItemListener listener;

    public ChooseCategoryImageModel(int id, List<Integer> drawables,String name, Uri uri, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.drawables = drawables;
        this.uri = uri;
        this.listener = listener;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
