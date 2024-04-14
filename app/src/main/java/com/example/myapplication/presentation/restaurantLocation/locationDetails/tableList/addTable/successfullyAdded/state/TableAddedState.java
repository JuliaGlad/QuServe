package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable.successfullyAdded.state;

import android.net.Uri;

public interface TableAddedState {
    class Success implements TableAddedState{
        public Uri data;

        public Success(Uri data) {
            this.data = data;
        }
    }
    class Loading implements TableAddedState{}

    class Error implements TableAddedState{}
}
