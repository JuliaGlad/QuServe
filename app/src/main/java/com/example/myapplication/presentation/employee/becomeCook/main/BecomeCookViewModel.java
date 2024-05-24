package com.example.myapplication.presentation.employee.becomeCook.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;
import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.example.myapplication.presentation.employee.becomeCook.state.BecomeCookState;
import com.example.myapplication.presentation.employee.becomeCook.state.BecomeCookStateModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BecomeCookViewModel extends ViewModel {

    private final MutableLiveData<BecomeCookState> _state = new MutableLiveData<>(new BecomeCookState.Loading());
    LiveData<BecomeCookState> state = _state;

    private final MutableLiveData<Boolean> _isComplete = new MutableLiveData<>(false);
    LiveData<Boolean> isComplete = _isComplete;

    public void getData(String path) {
        RestaurantUserDI.getRestaurantNameByEmployeePathUseCase.invoke(path)
                .zipWith(RestaurantEmployeeDI.getCookQrCodeByPathUseCase.invoke(path), (restaurantNameIdModel, uri) ->
                        new BecomeCookStateModel(
                        restaurantNameIdModel.getName(),
                        restaurantNameIdModel.getId(),
                        uri
                ))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<BecomeCookStateModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull BecomeCookStateModel becomeCookStateModel) {
                        _state.postValue(new BecomeCookState.Success(becomeCookStateModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new BecomeCookState.Error());
                    }
                });
    }

    public void addCook(String path) {
        RestaurantEmployeeDI.addCookUseCase.invoke(path)
                .concatWith(ProfileEmployeeDI.addCookEmployeeRoleUseCase.invoke(path))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isComplete.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}