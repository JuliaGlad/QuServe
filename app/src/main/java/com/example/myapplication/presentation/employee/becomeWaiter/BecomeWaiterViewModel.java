package com.example.myapplication.presentation.employee.becomeWaiter;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;
import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.example.myapplication.domain.model.restaurant.RestaurantNameIdModel;
import com.example.myapplication.presentation.employee.becomeWaiter.state.BecomeWaiterModel;
import com.example.myapplication.presentation.employee.becomeWaiter.state.BecomeWaiterState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BecomeWaiterViewModel extends ViewModel {

    private final MutableLiveData<BecomeWaiterState> _state = new MutableLiveData<>(new BecomeWaiterState.Loading());
    LiveData<BecomeWaiterState> state = _state;

    private final MutableLiveData<Boolean> _isAdded = new MutableLiveData<>();
    LiveData<Boolean> isAdded = _isAdded;

    public void getRestaurantName(String waiterPath) {
        RestaurantUserDI.getRestaurantNameByEmployeePathUseCase.invoke(waiterPath)
                .zipWith(RestaurantEmployeeDI.getWaiterQrCodeByPathUseCase.invoke(waiterPath), new BiFunction<RestaurantNameIdModel, Uri, BecomeWaiterModel>() {
                    @Override
                    public BecomeWaiterModel apply(RestaurantNameIdModel restaurantNameIdModel, Uri uri) throws Throwable {
                        return new BecomeWaiterModel(
                                restaurantNameIdModel.getName(),
                                restaurantNameIdModel.getId(),
                                uri
                        );
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<BecomeWaiterModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull BecomeWaiterModel becomeWaiterModel) {
                        _state.postValue(new BecomeWaiterState.Success(becomeWaiterModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new BecomeWaiterState.Error());
                    }
                });
    }

    public void addWaiter(String waiterPath){
        RestaurantEmployeeDI.addWaiterUseCase.invoke(waiterPath)
                .concatWith(ProfileEmployeeDI.addWaiterEmployeeRoleUseCase.invoke(waiterPath))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isAdded.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}