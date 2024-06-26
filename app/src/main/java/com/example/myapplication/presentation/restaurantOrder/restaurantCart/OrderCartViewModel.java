package com.example.myapplication.presentation.restaurantOrder.restaurantCart;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.di.restaurant.RestaurantTableDI;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.presentation.restaurantOrder.CartDishModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.model.OrderDishesModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.state.OrderCartState;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.state.OrderStateModel;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderCartViewModel extends ViewModel {

    private final MutableLiveData<Integer> _price = new MutableLiveData<>(0);
    LiveData<Integer> price = _price;

    private final MutableLiveData<String> _isOrdered = new MutableLiveData<>(null);
    LiveData<String> isOrdered = _isOrdered;

    private final MutableLiveData<OrderCartState> _state = new MutableLiveData<>(new OrderCartState.Loading());
    LiveData<OrderCartState> state = _state;

    public void increasePrice(int newPrice) {
        int previousPrice = price.getValue();
        _price.setValue(previousPrice + newPrice);
    }

    public void decreasePrice(int newPrice){
        int previousPrice = price.getValue();
        _price.setValue(previousPrice - newPrice);
    }

    public void getCartItems(String currentRestaurant) {
        List<CartDishModel> models = RestaurantOrderDI.getCartUseCase.invoke();
        String orderRestaurantId = RestaurantOrderDI.getOrderRestaurantIdUseCase.invoke();

        List<String> ids = models.stream().map(CartDishModel::getDishId).collect(Collectors.toList());
        if (orderRestaurantId != null) {
            if (currentRestaurant.equals(orderRestaurantId)) {
                RestaurantOrderDI.getDishesImagesByIdsUseCase.invoke(currentRestaurant, ids)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new SingleObserver<List<ImageTaskNameModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull List<ImageTaskNameModel> imageTaskNameModels) {
                                _state.postValue(new OrderCartState.Success(new OrderStateModel(
                                        models, imageTaskNameModels
                                )));
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                _state.postValue(new OrderCartState.Error());
                            }
                        });
            } else {
                RestaurantOrderDI.deleteOrderUseCase.invoke();
                _state.postValue(new OrderCartState.Success(null));
            }
        } else {
            _state.postValue(new OrderCartState.Success(null));
        }
    }

    public void createOrder(String restaurantId, String tableId, String totalPrice, String path, List<OrderDishesModel> models) {
        String orderId = generateOrderId();

        RestaurantOrderDI.addToActiveOrdersUseCase.invoke(restaurantId, tableId, path, orderId, totalPrice, models)
                .flatMapCompletable(pathOrder -> ProfileDI.addActiveRestaurantOrderUseCase.invoke(pathOrder))
                .concatWith(RestaurantOrderDI.addToTableListOrdersUseCase.invoke(path, orderId))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isOrdered.postValue(orderId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private String generateOrderId() {
        Random rand = new Random();
        int id = rand.nextInt(90000000) + 10000000;
        return "@" + id;
    }

    public void removeItemFromCart(String dishId) {
        RestaurantOrderDI.removeFromCartUseCase.invoke(dishId);
    }

    public void increaseAmount(CartDishModel model) {
        RestaurantOrderDI.incrementDishAmountUseCase.invoke(model);
    }

    public void decrementAmount(CartDishModel model) {
        RestaurantOrderDI.decrementDishAmountUseCase.invoke(model);
    }

    public String getTableId(String path) {
        return RestaurantTableDI.getTableIdByPathUseCase.invoke(path);
    }

    public String getOrderPath(String tablePath, String orderId) {
        return RestaurantOrderDI.getOrderPathByTablePathAndOrderIdUseCase.invoke(tablePath, orderId);
    }
}