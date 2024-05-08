package com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.example.myapplication.domain.model.restaurant.order.OrderModel;
import com.example.myapplication.domain.model.restaurant.order.OrderShortModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.state.AvailableCookOrdersState;
import com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.state.AvailableOrdersStateModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AvailableCookOrdersViewModel extends ViewModel {

    private final MutableLiveData<AvailableCookOrdersState> _state = new MutableLiveData<>(new AvailableCookOrdersState.Loading());
    LiveData<AvailableCookOrdersState> state = _state;

    private final MutableLiveData<Integer> _isTaken = new MutableLiveData<>(null);
    LiveData<Integer> isTaken = _isTaken;

    public void getOrders(String restaurantId, String locationId) {
        RestaurantUserDI.getNonTakenOrdersByRestaurantIdUseCase.invoke(restaurantId, locationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<OrderShortModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<OrderShortModel> orderModels) {
                      List<AvailableOrdersStateModel> models =  orderModels.stream().map(orderModel -> new AvailableOrdersStateModel(
                                        orderModel.getOrderId(),
                                        orderModel.getPath(),
                                        orderModel.getTableNumber(),
                                        orderModel.getDishesCount()
                                ))
                                .collect(Collectors.toList());
                      _state.postValue(new AvailableCookOrdersState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void takeOrder(int index, String restaurantId, String locationId, String orderId){
        RestaurantOrderDI.takeOrderByCookUseCase.invoke(restaurantId, locationId, orderId)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isTaken.postValue(index);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}