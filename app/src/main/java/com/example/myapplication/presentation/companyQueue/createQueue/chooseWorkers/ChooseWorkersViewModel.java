package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.CompanyQueueUserDI;
import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.model.EmployeeStateModel;
import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.state.ChooseWorkersState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChooseWorkersViewModel extends ViewModel {

    private final MutableLiveData<ChooseWorkersState> _state = new MutableLiveData<>(new ChooseWorkersState.Loading());
    LiveData<ChooseWorkersState> state = _state;

    public void getEmployees(String companyId) {
        CompanyQueueUserDI.getEmployeesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<EmployeeMainModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EmployeeMainModel> employeeMainModels) {

                        List<EmployeeStateModel> stateModels = new ArrayList<>();

                        for (int i = 0; i < employeeMainModels.size(); i++) {
                            EmployeeMainModel current = employeeMainModels.get(i);
                            stateModels.add(new EmployeeStateModel(
                                    current.getName(),
                                    current.getId(),
                                    current.getRole()
                            ));
                        }

                        _state.postValue(new ChooseWorkersState.Success(stateModels));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}