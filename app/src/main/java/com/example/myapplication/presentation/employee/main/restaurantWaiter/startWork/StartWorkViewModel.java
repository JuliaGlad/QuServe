package com.example.myapplication.presentation.employee.main.restaurantWaiter.startWork;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;
import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.startWork.state.StartWorkState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StartWorkViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isStarted = new MutableLiveData<>(false);
    LiveData<Boolean> isStarted = _isStarted;

    private final MutableLiveData<StartWorkState> _name = new MutableLiveData<>(new StartWorkState.Loading());
    LiveData<StartWorkState> name = _name;

    public void getRestaurantName(String restaurantId) {
        RestaurantUserDI.getRestaurantNameByIdsUseCase.invoke(restaurantId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String name) {
                        _name.postValue(new StartWorkState.Success(name));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _name.postValue(new StartWorkState.Error());
                    }
                });
    }

    public void startWorking(String restaurantId, String locationId){
        RestaurantEmployeeDI.updateIsWorkingUseCase.invoke(restaurantId, locationId,true)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isStarted.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}