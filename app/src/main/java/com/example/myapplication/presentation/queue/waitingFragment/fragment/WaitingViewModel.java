package com.example.myapplication.presentation.queue.waitingFragment.fragment;

import static com.example.myapplication.presentation.utils.Utils.EDIT_ESTIMATED_TIME;
import static com.example.myapplication.presentation.utils.Utils.EDIT_PEOPLE_BEFORE_YOU;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.queue.QueueModel;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.model.WaitingModel;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.leaveQueueItem.LeaveQueueDelegateItem;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.leaveQueueItem.LeaveQueueModel;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem.WaitingItemDelegateItem;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem.WaitingItemModel;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.state.WaitingState;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewModel;

public class WaitingViewModel extends ViewModel {

    private String queueId, nameString;
    private int midTime = 0;
    private int peopleBeforeSize = 0;

    private final MutableLiveData<WaitingState> _state = new MutableLiveData<>(new WaitingState.Loading());
    LiveData<WaitingState> state = _state;

    public Completable leaveQueue() {
        return DI.removeParticipantById.invoke(queueId);
    }

    public void getQueue(Fragment fragment) {
        DI.getQueueByParticipantIdUseCase.invoke()
                .flatMapObservable(queueModel -> {
                    List<String> participantsList = queueModel.getParticipants();
                    for (int i = 0; i < participantsList.size(); i++) {
                        if (checkParticipantsIndex(participantsList, i)) {
                            peopleBeforeSize = i + 1;
                            midTime = Integer.parseInt(queueModel.getMidTime()) * peopleBeforeSize;
                            break;
                        }
                    }

                    _state.postValue(new WaitingState.Success(new WaitingModel(
                            queueModel.getName(),
                            queueModel.getParticipants(),
                            queueModel.getId(),
                            queueModel.getMidTime()
                    )));

                    queueId = queueModel.getId();
                    nameString = queueModel.getName();
                    return DI.addSnapshotQueueUseCase.invoke(queueId);
                })
                .flatMapCompletable(documentSnapshot -> {
                    String date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
                    String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    return DI.addQueueToHistoryUseCase.invoke(queueId, nameString, time, date);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        fragment.requireActivity().finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public boolean checkParticipantsIndex(List<String> participants, int index){
       return DI.checkParticipantIndexUseCase.invoke(participants, index);
    }

    public void updateParticipateInQueue() {
        DI.updateParticipateInQueueUseCase.invoke(false);
    }
}