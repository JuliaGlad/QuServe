package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.state;

import java.util.List;

public interface CookActiveOrdersState {

    class Success implements CookActiveOrdersState{
        public List<CookActiveOrderStateModel> data;

        public Success(List<CookActiveOrderStateModel> data) {
            this.data = data;
        }
    }

    class Loading implements CookActiveOrdersState{

    }

    class Error implements CookActiveOrdersState{

    }

}
