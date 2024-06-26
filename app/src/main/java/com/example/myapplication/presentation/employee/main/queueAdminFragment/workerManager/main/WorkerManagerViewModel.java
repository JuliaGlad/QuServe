package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.main;

import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueUserDI;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.main.state.WorkerManagerState;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.model.CompanyEmployeeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerManagerViewModel extends ViewModel {

    private final MutableLiveData<WorkerManagerState> _state = new MutableLiveData<>(new WorkerManagerState.Loading());
    LiveData<WorkerManagerState> state = _state;

    public void getEmployees(String companyId) {
        CompanyQueueUserDI.getEmployeesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<EmployeeMainModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EmployeeMainModel> employeeMainModels) {
                        List<CompanyEmployeeModel> models = new ArrayList<>();
                        for (int i = 0; i < employeeMainModels.size(); i++) {
                            EmployeeMainModel current = employeeMainModels.get(i);
                            if (current.getRole().equals(WORKER)) {
                                models.add(new CompanyEmployeeModel(
                                        current.getName(),
                                        current.getId(),
                                        WORKER,
                                        current.getActiveQueues()
                                ));
                            }
                        }
                        _state.postValue(new WorkerManagerState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new WorkerManagerState.Error());
                    }
                });
    }
}