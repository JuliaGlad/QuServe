package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.domain.model.company.CompanyQueueManagerModel;
import com.example.myapplication.presentation.employee.main.ActiveQueueModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.model.AddQueueModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.state.AddQueueState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isAdded = new MutableLiveData<>(false);
    LiveData<Boolean> isAdded = _isAdded;

    private final MutableLiveData<AddQueueState> _state = new MutableLiveData<>(new AddQueueState.Loading());
    LiveData<AddQueueState> state = _state;

    public void getCompanyQueues(String companyId, List<String> ids) {
        CompanyQueueDI.getCompaniesQueuesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<CompanyQueueManagerModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CompanyQueueManagerModel> companyQueueManagerModels) {
                        List<AddQueueModel> models = new ArrayList<>();
                        for (int i = 0; i < companyQueueManagerModels.size(); i++) {
                            CompanyQueueManagerModel current = companyQueueManagerModels.get(i);
                            if (!ids.contains(current.getId())) {
                                models.add(new AddQueueModel(
                                        current.getId(),
                                        current.getLocation(),
                                        current.getName(),
                                        current.getCity(),
                                        current.getWorkersCount()
                                ));
                            }
                        }
                        _state.postValue(new AddQueueState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void addEmployeeToQueue(List<ActiveQueueModel> models, String companyId, String employeeId, String employeeName){
       CompanyQueueDI.addEmployeeToListQueues.invoke(models, companyId, employeeId, employeeName)
               .subscribeOn(Schedulers.io())
               .subscribe(new CompletableObserver() {
                   @Override
                   public void onSubscribe(@NonNull Disposable d) {

                   }

                   @Override
                   public void onComplete() {
                        _isAdded.postValue(true);
                   }

                   @Override
                   public void onError(@NonNull Throwable e) {

                   }
               });
    }
}