package com.example.myapplication.presentation.queue.participantList;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.QueueParticipantsListModel;
import com.example.myapplication.domain.model.QueueSizeModel;
import com.example.myapplication.presentation.queue.participantList.participantListItem.ParticipantListDelegateItem;
import com.example.myapplication.presentation.queue.participantList.participantListItem.ParticipantListModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewModel;

public class ParticipantsListViewModel extends ViewModel {

    private int participantsSize;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> item = _items;
    private final List<DelegateItem> participantList = new ArrayList<>();

    private void initRecyclerView(Fragment fragment, String queueID) {
        addParticipantListDelegateItems(fragment, participantsSize, queueID);
        _items.postValue(participantList);
    }

    private void addParticipantListDelegateItems(Fragment fragment, int queueLength, String queueID) {
        if (queueLength != 0) {
            for (int i = 0; i < queueLength; i++) {
                participantList.add(new ParticipantListDelegateItem(new ParticipantListModel(i, fragment.getString(R.string.participant))));
            }
        } else {
            Log.d("Participant List", "No users yet :)");
            participantList.add(new StringTextViewDelegateItem(new StringTextViewModel(1, "No user yet", 24, View.TEXT_ALIGNMENT_CENTER)));
        }
    }

    public void getParticipantsList(Fragment fragment) {
        DI.getParticipantsList.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueParticipantsListModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull QueueParticipantsListModel queueParticipantsListModel) {
                        if (queueParticipantsListModel.getParticipants().size() == 0) {
                            participantsSize = 0;
                        } else {
                            participantsSize = queueParticipantsListModel.getParticipants().size();
                        }
                        initRecyclerView(fragment, queueParticipantsListModel.getId());
                        addDocumentSnapshot(fragment, queueParticipantsListModel.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public void addDocumentSnapshot(Fragment fragment, String queueID) {
        DI.addDocumentSnapShot.invoke(queueID)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<QueueSizeModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull QueueSizeModel queueSizeModel) {
                        if (participantsSize < queueSizeModel.getSize()) {
                            List<DelegateItem> newItems = new ArrayList<>();
                                newItems.addAll(_items.getValue());
                                participantsSize = queueSizeModel.getSize();
                                newItems.add(new ParticipantListDelegateItem(new ParticipantListModel(participantsSize + 1, fragment.getString(R.string.participant))));
                                _items.setValue(newItems);
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

    public void nextParticipant() {
        DI.getParticipantsList.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueParticipantsListModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueParticipantsListModel queueParticipantsListModel) {
                        String name = queueParticipantsListModel.getParticipants().get(0).replace("[", "").replace("]", "");
                        DI.nextParticipantUseCase.invoke(queueParticipantsListModel.getId(), name);
                        List<DelegateItem> newItems = new ArrayList<>();
                        newItems.addAll(_items.getValue());
                        newItems.remove(0);
                        participantsSize = newItems.size();
                        _items.postValue(newItems);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}


