package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.restaurant.order.OrderCookModel;
import com.example.myapplication.domain.model.restaurant.order.OrderShortModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.state.CookActiveOrderStateModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.state.CookActiveOrdersState;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CookActiveOrdersViewModel extends ViewModel {

    private final MutableLiveData<CookActiveOrdersState> _state = new MutableLiveData<>(new CookActiveOrdersState.Loading());
    LiveData<CookActiveOrdersState> state = _state;

    public void getOrders(String restaurantId, String locationId) {
        RestaurantOrderDI.getActiveOrdersUseCase.invoke(restaurantId, locationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<OrderShortModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<OrderShortModel> orderShortModels) {
                        List<CookActiveOrderStateModel> models =
                               orderShortModels.stream()
                                        .map(orderShortModel -> new CookActiveOrderStateModel(
                                                orderShortModel.getTableNumber(),
                                                orderShortModel.getPath(),
                                                orderShortModel.getOrderId(),
                                                orderShortModel.getDishesCount())
                                        )
                                        .collect(Collectors.toList());
                        _state.postValue(new CookActiveOrdersState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new CookActiveOrdersState.Error());
                    }
                });
    }
}