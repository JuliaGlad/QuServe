package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.state;

public interface TableOrderDetailsState {
    class Success implements TableOrderDetailsState{
        public TableOrderDetailsStateModel data;

        public Success(TableOrderDetailsStateModel data) {
            this.data = data;
        }
    }
    class Loading implements TableOrderDetailsState{}

    class Error implements TableOrderDetailsState{}
}
