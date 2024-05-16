package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;
import com.example.myapplication.domain.model.restaurant.RestaurantEmployeeModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.model.EmployeeModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.state.EmployeeState;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EmployeesRestaurantViewModel extends ViewModel {

    private final MutableLiveData<EmployeeState> _state = new MutableLiveData<>(new EmployeeState.Loading());
    LiveData<EmployeeState> state = _state;

    public void getEmployees(String companyId, String locationId) {
        RestaurantEmployeeDI.getEmployeesUseCase.invoke(companyId, locationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<RestaurantEmployeeModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<RestaurantEmployeeModel> restaurantEmployeeModels) {

                        List<EmployeeModel> employees = restaurantEmployeeModels.stream()
                                .map(restaurantEmployeeModel -> new EmployeeModel(
                                        restaurantEmployeeModel.getName(),
                                        restaurantEmployeeModel.getUserId(),
                                        restaurantEmployeeModel.getRole()
                                )).collect(Collectors.toList());

                        _state.postValue(new EmployeeState.Success(
                                employees
                        ));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new EmployeeState.Error());
                    }
                });
    }
}