package com.example.myapplication.presentation.employee.main.queueWorkerFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueUserDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.domain.model.profile.ActiveQueueEmployeeModel;
import com.example.myapplication.presentation.employee.main.ActiveQueueModel;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.model.QueueWorkerStateModel;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.state.QueueWorkerState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueueWorkerViewModel extends ViewModel {
    String companyId, companyName;

    private final MutableLiveData<QueueWorkerState> _state = new MutableLiveData<>(new QueueWorkerState.Loading());
    LiveData<QueueWorkerState> state = _state;

    public void getEmployeeActiveQueues(String companyId) {
        CompanyQueueUserDI.getSingleCompanyUseCase.invoke(companyId)
                .flatMap(companyNameAndEmailModel -> {
                    this.companyId = companyId;
                    companyName = companyNameAndEmailModel.getName();
                    return ProfileEmployeeDI.getActiveQueuesUseCase.invoke(companyId);
                })
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
                        _state.postValue(new QueueWorkerState.Success(new QueueWorkerStateModel(
                                companyName,
                                companyId,
                                models
                        )));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new QueueWorkerState.Error());
                    }
                });
    }

}