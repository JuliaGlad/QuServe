package com.example.myapplication.presentation.companyQueue.queueDetails.participantsList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.queue.QueueSizeModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.participantsList.state.CompanyParticipantsState;

import java.util.Collections;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CompanyQueueParticipantsListViewModel extends ViewModel {

    int participantsSize = 0;
    int peoplePassed = 0;

    private final MutableLiveData<CompanyParticipantsState> _state = new MutableLiveData<>(new CompanyParticipantsState.Loading());
    LiveData<CompanyParticipantsState> state = _state;

    private final MutableLiveData<Boolean> _removeParticipant = new MutableLiveData<>(false);
    LiveData<Boolean> removeParticipant = _removeParticipant;

    private final MutableLiveData<Boolean> _addParticipant = new MutableLiveData<>(false);
    LiveData<Boolean> addParticipant = _addParticipant;

    public void nextParticipant(String queueId, String companyId) {
        DI.getCompanyQueueParticipantsListUseCase.invoke(queueId, companyId)
                .concatMapCompletable(queueParticipantsListModel -> {
                    String name = queueParticipantsListModel.getParticipants().get(0).replace("[", "").replace("]", "");
                    return DI.nextParticipantUseCompanyUseCase.invoke(queueId, companyId, name, peoplePassed);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        peoplePassed += 1;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getParticipantsList(String queueId, String companyId) {
        DI.getCompanyQueueParticipantsListUseCase.invoke(queueId, companyId)
                .flatMapObservable(queueParticipantsListModel -> {

                    if (!queueParticipantsListModel.getParticipants().equals(Collections.emptyList())) {
                        participantsSize = queueParticipantsListModel.getParticipants().size();
                    }

                    peoplePassed = 0;

                    _state.postValue(new CompanyParticipantsState.Success(queueParticipantsListModel.getParticipants()));

                    return DI. addCompanyQueueParticipantsSizeSnapshot.invoke(companyId, queueParticipantsListModel.getId());
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<QueueSizeModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull QueueSizeModel queueSizeModel) {
                        if (participantsSize < queueSizeModel.getSize()) {
                            participantsSize = queueSizeModel.getSize();
                            _addParticipant.postValue(true);

                        } else if (participantsSize > queueSizeModel.getSize()) {
                            participantsSize = queueSizeModel.getSize();
                            _removeParticipant.postValue(true);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}