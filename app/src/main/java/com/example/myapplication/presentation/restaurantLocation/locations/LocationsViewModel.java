package com.example.myapplication.presentation.restaurantLocation.locations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.RestaurantDI;
import com.example.myapplication.domain.model.restaurant.location.RestaurantLocationModel;
import com.example.myapplication.presentation.restaurantLocation.locations.model.LocationsModel;
import com.example.myapplication.presentation.restaurantLocation.locations.state.LocationsState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocationsViewModel extends ViewModel {

    private final MutableLiveData<LocationsState> _state = new MutableLiveData<>(new LocationsState.Loading());
    LiveData<LocationsState> state = _state;

    public void getRestaurantLocations(String restaurantId){
        RestaurantDI.getRestaurantLocationsUseCase.invoke(restaurantId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<RestaurantLocationModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<RestaurantLocationModel> restaurantLocationModels) {
                        List<LocationsModel> models = new ArrayList<>();
                        for (int i = 0; i < restaurantLocationModels.size(); i++) {
                            RestaurantLocationModel current = restaurantLocationModels.get(i);
                            models.add(new LocationsModel(
                                    current.getLocationId(),
                                    current.getLocation(),
                                    current.getCity(),
                                    current.getCooksCount(),
                                    current.getWaitersCount()
                            ));
                        }
                        _state.postValue(new LocationsState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}