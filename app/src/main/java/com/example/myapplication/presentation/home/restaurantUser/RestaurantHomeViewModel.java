package com.example.myapplication.presentation.home.restaurantUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantLocationDI;
import com.example.myapplication.domain.model.restaurant.location.RestaurantLocationModel;
import com.example.myapplication.presentation.home.restaurantUser.state.RestaurantHomeLocationModel;
import com.example.myapplication.presentation.home.restaurantUser.state.RestaurantHomeState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantHomeViewModel extends ViewModel {

    private final MutableLiveData<RestaurantHomeState> _state = new MutableLiveData<>(new RestaurantHomeState.Loading());
    LiveData<RestaurantHomeState> state = _state;

    public void getRestaurantLocations(String restaurantId) {
        RestaurantLocationDI.getRestaurantLocationsUseCase.invoke(restaurantId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<RestaurantLocationModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<RestaurantLocationModel> restaurantLocationModels) {
                        List<RestaurantHomeLocationModel> models = new ArrayList<>();

                        for (RestaurantLocationModel current : restaurantLocationModels) {
                            models.add(new RestaurantHomeLocationModel(
                                    current.getLocationId(),
                                    current.getLocation(),
                                    current.getOrdersCount()
                            ));
                        }

                        _state.postValue(new RestaurantHomeState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new RestaurantHomeState.Error());
                    }
                });
    }
}