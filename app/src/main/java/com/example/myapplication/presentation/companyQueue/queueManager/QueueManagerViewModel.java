package com.example.myapplication.presentation.companyQueue.queueManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.domain.model.company.CompanyQueueManagerModel;
import com.example.myapplication.presentation.companyQueue.queueManager.model.QueueManagerModel;
import com.example.myapplication.presentation.companyQueue.queueManager.state.QueueManagerState;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueueManagerViewModel extends ViewModel {

    private final MutableLiveData<QueueManagerState> _state = new MutableLiveData<>(new QueueManagerState.Loading());
    LiveData<QueueManagerState> state = _state;

    public void getList(String companyId) {
        CompanyQueueDI.getCompaniesQueuesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<CompanyQueueManagerModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CompanyQueueManagerModel> companyQueueManagerModels) {
                        List<QueueManagerModel> list = new ArrayList<>();
                        for (int i = 0; i < companyQueueManagerModels.size(); i++) {
                            CompanyQueueManagerModel current = companyQueueManagerModels.get(i);
                            list.add(new QueueManagerModel(
                                    current.getName(),
                                    current.getId(),
                                    current.getLocation(),
                                    current.getCity(),
                                    current.getWorkersCount())
                            );
                        }
                        _state.postValue(new QueueManagerState.Success(list));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}