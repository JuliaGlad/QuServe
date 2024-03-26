package com.example.myapplication.presentation.companyQueue.queueDetails.participantsList;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.queue.QueueParticipantsListModel;
import com.example.myapplication.domain.model.queue.QueueSizeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListModel;
import myapplication.android.ui.recycler.ui.items.items.statisticsDelegate.StatisticsDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.statisticsDelegate.StatisticsModel;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewModel;

public class CompanyQueueParticipantsListViewModel extends ViewModel {

    int participantsSize = 0;
    int peoplePassed = 0;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> item = _items;

    private final List<DelegateItem> itemsList = new ArrayList<>();


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

    public void getParticipantsList(Fragment fragment, String queueId, String companyId) {
        DI.getCompanyQueueParticipantsListUseCase.invoke(queueId, companyId)
                .flatMapObservable(queueParticipantsListModel -> {
                    if (!queueParticipantsListModel.getParticipants().equals(Collections.emptyList())) {
                        participantsSize = queueParticipantsListModel.getParticipants().size();
                    }

                    peoplePassed = 0;
                    initRecycler(fragment);

                    initRecycler(fragment);
                    return DI.addQueueSizeModelSnapShot.invoke(queueParticipantsListModel.getId());
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<QueueSizeModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull QueueSizeModel queueSizeModel) {
                        if (participantsSize < queueSizeModel.getSize()) {
                            List<DelegateItem> newItems = new ArrayList<>(_items.getValue());
                            participantsSize = queueSizeModel.getSize();
                            newItems.add(new ParticipantListDelegateItem(new ParticipantListModel(participantsSize + 1, fragment.getString(R.string.participant))));
                            _items.setValue(newItems);

                        } else if (participantsSize > queueSizeModel.getSize()) {

                            List<DelegateItem> newItems = new ArrayList<>(_items.getValue());
                            newItems.remove(0);
                            participantsSize = newItems.size();
                            _items.postValue(newItems);

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

    private void addParticipantListDelegateItems(Fragment fragment, int queueLength) {
        if (queueLength != 0) {
            for (int i = 0; i < queueLength; i++) {
                itemsList.add(new ParticipantListDelegateItem(new ParticipantListModel(i, fragment.getString(R.string.participant))));
            }
        } else {
            itemsList.add(new StringTextViewDelegateItem(new StringTextViewModel(1, "No user yet", 24, View.TEXT_ALIGNMENT_CENTER)));
        }
    }

    private void initRecycler(Fragment fragment) {
        itemsList.add(new StatisticsDelegateItem(new StatisticsModel(1)));
        addParticipantListDelegateItems(fragment, participantsSize);
        _items.postValue(itemsList);
    }

}