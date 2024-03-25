package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers;

import static com.example.myapplication.presentation.utils.Utils.CHOSEN;
import static com.example.myapplication.presentation.utils.Utils.NOT_CHOSEN;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.recycler.WorkerItemModel;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChooseWorkersViewModel extends ViewModel {

    private List<WorkerItemModel> list = new ArrayList<>();
    private final MutableLiveData<List<WorkerItemModel>> _employees = new MutableLiveData<>();
    LiveData<List<WorkerItemModel>> employees = _employees;

    public void getEmployees(String companyId, List<EmployeeModel> chosen) {
        DI.getEmployeesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<EmployeeMainModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EmployeeMainModel> employeeMainModels) {
                        for (int i = 0; i < employeeMainModels.size(); i++) {
                            if (employeeMainModels.get(i).getRole().equals(WORKER)) {
                                String state = NOT_CHOSEN;

                                if (chosen.size() > 0) {
                                    for (int j = 0; j < chosen.size(); j++) {
                                        if (chosen.get(j).getUserId().equals(employeeMainModels.get(i).getId())) {
                                            state = CHOSEN;
                                        }
                                    }
                                }

                                list.add(new WorkerItemModel(
                                        i,
                                        employeeMainModels.get(i).getName(),
                                        employeeMainModels.get(i).getId(),
                                        state,
                                        chosen
                                ));
                            }
                        }
                        _employees.postValue(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}