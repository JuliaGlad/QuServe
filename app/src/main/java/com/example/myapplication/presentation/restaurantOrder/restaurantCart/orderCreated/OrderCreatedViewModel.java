package com.example.myapplication.presentation.restaurantOrder.restaurantCart.orderCreated;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.orderCreated.state.OrderCreatedState;

public class OrderCreatedViewModel extends ViewModel {

    private final MutableLiveData<OrderCreatedState> _state = new MutableLiveData<>(new OrderCreatedState.Loading());
    LiveData<OrderCreatedState> state = _state;
}