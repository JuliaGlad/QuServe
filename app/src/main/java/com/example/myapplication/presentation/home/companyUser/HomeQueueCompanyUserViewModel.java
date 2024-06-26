package com.example.myapplication.presentation.home.companyUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.domain.model.company.CompanyQueueParticipantsSizeAndNameModel;
import com.example.myapplication.presentation.home.companyUser.models.QueueCompanyHomeModel;
import com.example.myapplication.presentation.home.companyUser.state.HomeQueueCompanyState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeQueueCompanyUserViewModel extends ViewModel {

    private final MutableLiveData<HomeQueueCompanyState> _state = new MutableLiveData<>(new HomeQueueCompanyState.Loading());
    LiveData<HomeQueueCompanyState> state = _state;

    public void getQueues(String companyId){
        CompanyQueueDI.getQueuesParticipantSizeAndNameUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<CompanyQueueParticipantsSizeAndNameModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CompanyQueueParticipantsSizeAndNameModel> companyQueues) {
                        List<QueueCompanyHomeModel> models = new ArrayList<>();
                        for (int i = 0; i < companyQueues.size(); i++) {
                            models.add(new QueueCompanyHomeModel(
                                    companyQueues.get(i).getQueueId(),
                                    companyQueues.get(i).getName(),
                                    companyQueues.get(i).getParticipantsSize()
                            ));
                        }
                        _state.postValue(new HomeQueueCompanyState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new HomeQueueCompanyState.Error());
                    }
                });
    }

}