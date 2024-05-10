package com.example.myapplication.presentation.employee.main.restaurantWaiter.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;
import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.example.myapplication.domain.model.restaurant.order.ReadyDishesModel;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.state.MainWaiterState;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.state.MainWaiterStateModel;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.state.WaiterReadyDishesModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainWaiterViewModel extends ViewModel {

    private final List<String> readyDishesDocs = new ArrayList<>();

    private final MutableLiveData<MainWaiterState> _state = new MutableLiveData<>(new MainWaiterState.Loading());
    LiveData<MainWaiterState> state = _state;

    private final MutableLiveData<Integer> _removed = new MutableLiveData<>(null);
    LiveData<Integer> removed = _removed;

    private final MutableLiveData<WaiterReadyDishesModel> _added = new MutableLiveData<>(null);
    LiveData<WaiterReadyDishesModel> added = _added;

    private final MutableLiveData<Boolean> _isWorking = new MutableLiveData<>(null);
    LiveData<Boolean> isWorking = _isWorking;

    public void checkIsWorking(String restaurantId, String locationId){
        RestaurantEmployeeDI.checkIsWorkingUseCase.invoke(restaurantId, locationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        _isWorking.postValue(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getOrders(String restaurantId, String locationId) {
        RestaurantOrderDI.getReadyDishesToWaiterUseCase.invoke(restaurantId, locationId)
                .zipWith(RestaurantUserDI.getRestaurantNameByIdsUseCase.invoke(restaurantId), (readyDishesModels, name) -> {
                    List<WaiterReadyDishesModel> models = new ArrayList<>();
                    for (ReadyDishesModel model : readyDishesModels) {
                        String docId = model.getOrderDocId();
                        models.add(new WaiterReadyDishesModel(
                                model.getTableNumber(),
                                model.getDishCount(),
                                model.getDishName(),
                                docId
                        ));
                        readyDishesDocs.add(docId);
                    }
                    return new MainWaiterStateModel(name, models);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<MainWaiterStateModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MainWaiterStateModel mainWaiterStateModel) {
                        addSnapshot(restaurantId, locationId);
                        _state.postValue(new MainWaiterState.Success(mainWaiterStateModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void onDishServed(int index, String restaurantId, String locationId, String readyDishDocId){
        RestaurantOrderDI.onDishServedUseCase.invoke(restaurantId, locationId, readyDishDocId)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _removed.postValue(index);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void addSnapshot(String restaurantId, String locationId) {
        RestaurantOrderDI.addReadyDishesSnapshotUseCase.invoke(restaurantId, locationId, readyDishesDocs)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ReadyDishesModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ReadyDishesModel readyDishesModel) {
                        String docId = readyDishesModel.getOrderDocId();
                        readyDishesDocs.add(docId);
                        _added.postValue(new WaiterReadyDishesModel(
                                readyDishesModel.getTableNumber(),
                                readyDishesModel.getDishCount(),
                                readyDishesModel.getDishName(),
                                readyDishesModel.getOrderDocId()
                        ));
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