package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.state;

import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.model.TableModel;

import java.util.List;

public interface TableListState {
    class Success implements TableListState{
        public List<TableModel> data;

        public Success(List<TableModel> data) {
            this.data = data;
        }
    }

    class Loading implements TableListState{}

    class Error implements TableListState{}
}
