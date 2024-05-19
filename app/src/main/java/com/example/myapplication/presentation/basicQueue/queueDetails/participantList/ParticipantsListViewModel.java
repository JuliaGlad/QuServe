
package com.example.myapplication.presentation.basicQueue.queueDetails.participantList;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.domain.model.queue.QueueSizeModel;
import com.example.myapplication.presentation.basicQueue.queueDetails.participantList.state.ParticipantsListState;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ParticipantsListViewModel extends ViewModel {

    private int participantsSize;
    private int peoplePassed;

    private final MutableLiveData<ParticipantsListState> _state = new MutableLiveData<>(new ParticipantsListState.Loading());
    LiveData<ParticipantsListState> state = _state;

    private final MutableLiveData<Boolean> _addParticipant = new MutableLiveData<>(false);
    LiveData<Boolean> addParticipant = _addParticipant;

    private final MutableLiveData<Boolean> _removeParticipant = new MutableLiveData<>(false);
    LiveData<Boolean> removeParticipant = _removeParticipant;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> item = _items;

    public void getParticipantsList() {
        QueueDI.getParticipantsListUseCase.invoke()
                .flatMapObservable(queueParticipantsListModel -> {
                    participantsSize = queueParticipantsListModel.getParticipants().size();
                    peoplePassed = 0;
                    _state.postValue(new ParticipantsListState.Success(queueParticipantsListModel.getParticipants()));

                    return QueueDI.addQueueSizeModelSnapShot.invoke(queueParticipantsListModel.getId());
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
                        _state.postValue(new ParticipantsListState.Error());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void nextParticipant() {
        QueueDI.getParticipantsListUseCase.invoke()
                .flatMapCompletable(queueParticipantsListModel -> {
                    String name = queueParticipantsListModel.getParticipants().get(0).replace("[", "").replace("]", "");
                    String id = queueParticipantsListModel.getId();
                    return QueueDI.nextParticipantUseCase.invoke(id, name, peoplePassed);
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
                        Log.e("NextParticipantException", e.getMessage());
                    }
                });
    }
}