package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.chooseLocation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantLocationDI;
import com.example.myapplication.domain.model.restaurant.location.RestaurantLocationModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.chooseLocation.state.ChooseLocationState;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.chooseLocation.state.ChooseLocationStateModel;
import com.example.myapplication.presentation.restaurantLocation.locations.model.LocationsModel;
import com.example.myapplication.presentation.restaurantLocation.locations.state.LocationsState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChooseLocationViewModel extends ViewModel {

    private final MutableLiveData<ChooseLocationState> _state = new MutableLiveData<>(new ChooseLocationState.Loading());
    LiveData<ChooseLocationState> state = _state;

    public void getRestaurantLocations(String restaurantId){
        RestaurantLocationDI.getRestaurantLocationsUseCase.invoke(restaurantId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<RestaurantLocationModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<RestaurantLocationModel> restaurantLocationModels) {
                        List<ChooseLocationStateModel> models = new ArrayList<>();
                        for (int i = 0; i < restaurantLocationModels.size(); i++) {
                            RestaurantLocationModel current = restaurantLocationModels.get(i);
                            models.add(new ChooseLocationStateModel(
                                    current.getLocationId(),
                                    current.getLocation(),
                                    current.getCity(),
                                    current.getCooksCount(),
                                    current.getWaitersCount()
                            ));
                        }
                        _state.postValue(new ChooseLocationState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}