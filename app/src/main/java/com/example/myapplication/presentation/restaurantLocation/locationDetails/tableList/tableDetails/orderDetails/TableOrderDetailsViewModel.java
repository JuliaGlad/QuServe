package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.restaurant.order.OrderTableDetailsModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.state.TableDetailsDishModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.state.TableOrderDetailsState;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.state.TableOrderDetailsStateModel;

import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TableOrderDetailsViewModel extends ViewModel {

    private final MutableLiveData<TableOrderDetailsState> _state = new MutableLiveData<>(new TableOrderDetailsState.Loading());
    LiveData<TableOrderDetailsState> state = _state;

    public void getTableOrderData(String restaurantId, String locationId, String orderId) {
        RestaurantOrderDI.getOrderByIdsUseCase.invoke(restaurantId, locationId, orderId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<OrderTableDetailsModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull OrderTableDetailsModel orderTableDetailsModel) {
                        _state.postValue(new TableOrderDetailsState.Success(new TableOrderDetailsStateModel(
                                orderTableDetailsModel.getCook(),
                                orderTableDetailsModel.getWaiter(),
                                orderTableDetailsModel.getModels().stream().map(dishShortInfoModel ->
                                        new TableDetailsDishModel(
                                                dishShortInfoModel.getName(),
                                                dishShortInfoModel.getCount())
                                ).collect(Collectors.toList())
                        )));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new TableOrderDetailsState.Error());
                    }
                });
    }

}