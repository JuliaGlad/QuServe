package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantTableDI;
import com.example.myapplication.domain.model.restaurant.table.RestaurantTableModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.model.TableModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.state.TableListState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TableListViewModel extends ViewModel {

    private final MutableLiveData<TableListState> _state = new MutableLiveData<>(new TableListState.Loading());
    LiveData<TableListState> state = _state;

    public void getTables(String restaurantId, String locationId){
        RestaurantTableDI.getRestaurantTablesUseCase.invoke(restaurantId, locationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<RestaurantTableModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<RestaurantTableModel> restaurantTableModels) {
                        List<TableModel> tables = new ArrayList<>();
                        for (RestaurantTableModel model : restaurantTableModels) {
                            tables.add(new TableModel(
                                    model.getTableNumber(),
                                    model.getTableId(),
                                    model.getOrderId()
                            ));
                        }
                        _state.postValue(new TableListState.Success(tables));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}