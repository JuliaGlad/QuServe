package com.example.myapplication.presentation.common.orderDetails;

import static com.example.myapplication.presentation.utils.constants.Restaurant.VISITOR;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.di.restaurant.RestaurantTableDI;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.domain.model.restaurant.order.OrderDetailsDishUseCaseModel;
import com.example.myapplication.presentation.common.orderDetails.state.OrderDetailsDishModel;
import com.example.myapplication.presentation.common.orderDetails.state.OrderDetailsState;
import com.example.myapplication.presentation.common.orderDetails.state.OrderDetailsStateModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.state.RestaurantOrderDishDetailsState;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderDetailsViewModel extends ViewModel {
    private String orderId, totalPrice, tableId;

    private final MutableLiveData<OrderDetailsState> _state = new MutableLiveData<>(new OrderDetailsState.Loading());
    LiveData<OrderDetailsState> state = _state;

    public void getOrder(String path, String type) {
        List<OrderDetailsDishModel> dishes = new ArrayList<>();
        List<OrderDetailsDishUseCaseModel> models = new ArrayList<>();

        RestaurantOrderDI.getOrderByPathUseCase.invoke(path)
                .flatMap(orderDetailsModel -> {
                    models.addAll(orderDetailsModel.getModels());
                    tableId = orderDetailsModel.getTableId();
                    orderId = orderDetailsModel.getOrderId();
                    totalPrice = orderDetailsModel.getTotalPrice();
                    List<String> ids = orderDetailsModel.getModels()
                            .stream()
                            .map(OrderDetailsDishUseCaseModel::getDishId)
                            .collect(Collectors.toList());
                    return RestaurantOrderDI.getDishesImagesByIdsUseCase.invoke(orderDetailsModel.getRestaurantId(), ids);
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
                            dishes.add(new OrderDetailsDishModel(
                                    currentDish.getDishId(),
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

                        if (type.equals(VISITOR)) {
                            addSnapshot(path, tableId);
                        }

                        _state.postValue(new OrderDetailsState.Success(new OrderDetailsStateModel(
                                orderId,
                                tableId,
                                totalPrice,
                                dishes
                        )));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new OrderDetailsState.Error());
                    }
                });
    }

    private void addSnapshot(String orderPath, String tableId) {
        String tablePath = RestaurantTableDI.tableIdByOrderPathAndTableIdUseCase.invoke(orderPath, tableId);
        RestaurantOrderDI.addTableSnapshotUseCase.invoke(tablePath)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DocumentSnapshot documentSnapshot) {
                        if (RestaurantOrderDI.onOrderFinishedUseCase.invoke(documentSnapshot)) {
                            _state.postValue(new OrderDetailsState.OrderIsFinished());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}