package com.example.myapplication.presentation.home.basicUser;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;
import com.example.myapplication.domain.model.queue.QueueIdAndNameModel;
import com.example.myapplication.domain.model.queue.QueueModel;
import com.example.myapplication.presentation.home.basicUser.model.CompanyBasicUserModel;
import com.example.myapplication.presentation.home.basicUser.model.HomeBasicUserBooleanModel;
import com.example.myapplication.presentation.home.basicUser.model.HomeBasicUserModel;
import com.example.myapplication.presentation.home.basicUser.model.QueueBasicUserHomeModel;
import com.example.myapplication.presentation.home.basicUser.state.HomeBasicUserState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeBasisUserViewModel extends ViewModel {

    private boolean isCompanyOwner = false;
    private boolean isQueueParticipant = false;
    private boolean isQueueOwner = false;

    QueueBasicUserHomeModel ownerQueue = null;
    QueueBasicUserHomeModel participantQueue = null;
    List<CompanyBasicUserModel> companies = new ArrayList<>();

    private final MutableLiveData<Boolean> _getCompanies = new MutableLiveData<>(false);
    LiveData<Boolean> getCompanies = _getCompanies;

    private final MutableLiveData<Boolean> _getQueueByAuthorId = new MutableLiveData<>(false);
    LiveData<Boolean> getQueueById = _getQueueByAuthorId;

    private final MutableLiveData<HomeBasicUserState> _state = new MutableLiveData<>(new HomeBasicUserState.Loading());
    LiveData<HomeBasicUserState> state = _state;

    public void getUserBooleanData() {
        DI.checkCompanyExistUseCase.invoke()
                .zipWith(DI.getUserBooleanDataUseCase.invoke(), (companyBoolean, userBooleanDataModel) ->
                        new HomeBasicUserBooleanModel(
                                userBooleanDataModel.isOwnQueue(),
                                userBooleanDataModel.isParticipateInQueue(),
                                companyBoolean)
                )
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<HomeBasicUserBooleanModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull HomeBasicUserBooleanModel homeBasicUserBooleanModel) {
                        if (!homeBasicUserBooleanModel.isCompanyOwner() && !homeBasicUserBooleanModel.isParticipateInQueue() && !homeBasicUserBooleanModel.isOwnQueue()) {
                            _state.postValue(new HomeBasicUserState.Success(null));
                        } else {
                            isCompanyOwner = homeBasicUserBooleanModel.isCompanyOwner();
                            isQueueParticipant = homeBasicUserBooleanModel.isParticipateInQueue();
                            isQueueOwner = homeBasicUserBooleanModel.isOwnQueue();
                            Log.d("queue owner", String.valueOf(isQueueOwner));
                            getCompanies();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getCompanies() {
        if (isCompanyOwner) {
            DI.getCompanyUseCase.invoke()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<List<CompanyNameIdModel>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull List<CompanyNameIdModel> companyNameIdModels) {
                            for (int i = 0; i < companyNameIdModels.size(); i++) {
                                CompanyNameIdModel current = companyNameIdModels.get(i);
                                companies.add(new CompanyBasicUserModel(current.getId(), current.getName()));
                            }
                            _getCompanies.postValue(true);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            _getCompanies.postValue(true);
        }
    }

    public void getQueueByParticipantId() {
        if (isQueueParticipant) {
            DI.getQueueByParticipantIdUseCase.invoke()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<QueueModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull QueueModel queueModel) {
                            participantQueue = new QueueBasicUserHomeModel(
                                    queueModel.getId(),
                                    queueModel.getName()
                            );
                            _state.postValue(new HomeBasicUserState.Success(new HomeBasicUserModel(companies, participantQueue, ownerQueue)));
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            _state.postValue(new HomeBasicUserState.Success(new HomeBasicUserModel(companies, participantQueue, ownerQueue)));
        }
    }

    public void getQueueByAuthorId() {
        if (isQueueOwner) {
            DI.getQueueByAuthorUseCase.invoke()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<QueueIdAndNameModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull QueueIdAndNameModel queueIdAndNameModel) {
                            ownerQueue = new QueueBasicUserHomeModel(
                                    queueIdAndNameModel.getId(),
                                    queueIdAndNameModel.getName()
                            );
                            _getQueueByAuthorId.postValue(true);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            _getQueueByAuthorId.postValue(true);
        }
    }
}