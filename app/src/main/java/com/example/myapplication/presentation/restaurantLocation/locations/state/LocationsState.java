package com.example.myapplication.presentation.restaurantLocation.locations.state;

import com.example.myapplication.presentation.restaurantLocation.locations.model.LocationsModel;

import java.util.List;

public interface LocationsState {
    class Success implements LocationsState{
       public List<LocationsModel> data;

        public Success(List<LocationsModel> data) {
            this.data = data;
        }
    }

    class Loading implements LocationsState{}

    class Error implements LocationsState{}
}
