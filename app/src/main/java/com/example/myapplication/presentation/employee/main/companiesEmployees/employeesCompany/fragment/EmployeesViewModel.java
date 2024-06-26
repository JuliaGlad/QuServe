package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesCompany.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueUserDI;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.model.EmployeeModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.state.EmployeeState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EmployeesViewModel extends ViewModel {

    private final MutableLiveData<EmployeeState> _state = new MutableLiveData<>();
    LiveData<EmployeeState> state = _state;

    public void getEmployees(String companyId) {
        CompanyQueueUserDI.getEmployeesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<EmployeeMainModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EmployeeMainModel> employeeMainModels) {
                        List<EmployeeModel> list = new ArrayList<>();
                        for (int i = 0; i < employeeMainModels.size(); i++) {
                            EmployeeMainModel current = employeeMainModels.get(i);
                            list.add(new EmployeeModel(
                                    current.getName(),
                                    current.getId(),
                                    current.getRole()
                            ));
                        }
                        _state.postValue(new EmployeeState.Success(list));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new EmployeeState.Error());
                    }
                });
    }


}