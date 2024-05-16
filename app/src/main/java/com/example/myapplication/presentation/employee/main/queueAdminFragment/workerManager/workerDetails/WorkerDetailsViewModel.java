package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.domain.model.profile.ActiveQueueEmployeeModel;
import com.example.myapplication.presentation.employee.main.ActiveQueueModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.state.WorkerDetailsState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerDetailsViewModel extends ViewModel {

    private final MutableLiveData<WorkerDetailsState> _state = new MutableLiveData<>(new WorkerDetailsState.Loading());
    LiveData<WorkerDetailsState> state = _state;

    public void getEmployeeData(String companyId, String employeeId) {
        ProfileEmployeeDI.getActiveQueuesByEmployeeIdUseCase.invoke(companyId, employeeId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<ActiveQueueEmployeeModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<ActiveQueueEmployeeModel> activeQueueEmployeeModels) {
                        List<ActiveQueueModel> models = new ArrayList<>();
                        for (int i = 0; i < activeQueueEmployeeModels.size(); i++) {
                            ActiveQueueEmployeeModel current = activeQueueEmployeeModels.get(i);
                            models.add(new ActiveQueueModel(
                                    current.getQueueId(),
                                    current.getQueueName(),
                                    current.getLocation()
                            ));
                        }
                        _state.postValue(new WorkerDetailsState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new WorkerDetailsState.Error());
                    }
                });
    }

}