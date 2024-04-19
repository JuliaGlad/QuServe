package com.example.myapplication.presentation.restaurantMenu.dishDetails.state;

import com.example.myapplication.presentation.restaurantMenu.dishDetails.model.DishDetailsStateModel;

public interface DishDetailsState {
    class Success implements DishDetailsState {
        public DishDetailsStateModel data;

        public Success(DishDetailsStateModel data) {
            this.data = data;
        }
    }

    class Loading implements DishDetailsState {
    }

    class Error implements DishDetailsState {
    }
}
