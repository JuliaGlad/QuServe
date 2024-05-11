package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.domain.model.restaurant.order.OrderDetailsDishUseCaseModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.state.OrderDetailsWithIndicatorsModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.state.OrderDetailsWithIndicatorsState;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.state.OrderDetailsWithIndicatorsStateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderDetailsWithIndicatorsViewModel extends ViewModel {

    private String orderId, totalPrice, restaurantId;

    private final MutableLiveData<OrderDetailsWithIndicatorsState> _state = new MutableLiveData<>(new OrderDetailsWithIndicatorsState.Loading());
    LiveData<OrderDetailsWithIndicatorsState> state = _state;

    private final MutableLiveData<Integer> _isReady = new MutableLiveData<>(null);
    LiveData<Integer> isReady = _isReady;

    private final MutableLiveData<Boolean> _isFinished = new MutableLiveData<>(false);
    LiveData<Boolean> isFinished = _isFinished;

    public void getOrder(String path) {
        List<OrderDetailsWithIndicatorsModel> dishes = new ArrayList<>();
        List<OrderDetailsDishUseCaseModel> models = new ArrayList<>();

        RestaurantOrderDI.getOrderWithNotTakenDishesUseCase.invoke(path)
                .flatMap(orderDetailsModel -> {
                    models.addAll(orderDetailsModel.getModels());
                    orderId = orderDetailsModel.getOrderId();
                    totalPrice = orderDetailsModel.getTotalPrice();
                    restaurantId = orderDetailsModel.getRestaurantId();
                    List<String> ids = orderDetailsModel.getModels()
                            .stream()
                            .map(OrderDetailsDishUseCaseModel::getDishId)
                            .collect(Collectors.toList());

                    return RestaurantOrderDI.getDishesImagesByIdsUseCase.invoke(restaurantId, ids);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<ImageTaskNameModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<ImageTaskNameModel> imageTaskNameModels) {
                        for (int i = 0; i < models.size(); i++) {
                            OrderDetailsDishUseCaseModel currentDish = models.get(i);
                            dishes.add(new OrderDetailsWithIndicatorsModel(
                                    currentDish.getDishId(),
                                    currentDish.getDocumentDishId(),
                                    currentDish.getAmount(),
                                    currentDish.getName(),
                                    currentDish.getWeight(),
                                    currentDish.getPrice(),
                                    imageTaskNameModels.get(i).getTask(),
                                    currentDish.getTopping(),
                                    currentDish.getRequiredChoice(),
                                    currentDish.getToRemove()
                            ));
                        }
                        _state.postValue(new OrderDetailsWithIndicatorsState.Success(new OrderDetailsWithIndicatorsStateModel(
                                orderId,
                                restaurantId,
                                totalPrice,
                                dishes
                        )));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void addToReadyDishes(int index, String orderDishId, String tableNumber, String dishName, String orderPath){
        RestaurantOrderDI.addToReadyDishesUseCase.invoke(orderDishId, tableNumber, dishName, orderPath)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        if (integer.intValue() == 0){
                            _isFinished.postValue(true);
                        } else {
                            _isReady.postValue(index);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void finishOrder(String orderPath) {
        RestaurantOrderDI.finishOrderByPathUseCase.invoke(orderPath)
                .concatWith(ProfileDI.removeRestaurantUserOrderUseCase.invoke())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _state.postValue(new OrderDetailsWithIndicatorsState.OrderIsFinished());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
