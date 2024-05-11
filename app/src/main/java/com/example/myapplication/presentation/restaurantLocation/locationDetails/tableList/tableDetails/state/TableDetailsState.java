package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.state;

import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.model.TableDetailModel;

public interface TableDetailsState {
    class Success implements TableDetailsState{
        public TableDetailsStateModel data;

        public Success(TableDetailsStateModel data) {
            this.data = data;
        }
    }

    class Loading implements TableDetailsState{}

    class Error implements TableDetailsState{}
}
