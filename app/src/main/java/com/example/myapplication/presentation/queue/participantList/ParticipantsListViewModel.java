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

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> item = _items;

    private final List<DelegateItem> participantList = new ArrayList<>();

    private void initRecyclerView(Fragment fragment, int queueLength) {
        addParticipantListDelegateItems(fragment, queueLength);
        _items.postValue(participantList);
    }

    private void addParticipantListDelegateItems(Fragment fragment, int queueLength) {
        if (queueLength != 0) {
            for (int i = 0; i < queueLength; i++) {
                int number = i + 1;
                participantList.add(new ParticipantListDelegateItem(new ParticipantListModel(2, fragment.getString(R.string.participant) + " " + "#" + number)));
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
                        int queueLength;
                        if (queueParticipantsListModel.getParticipants().get(0).equals("[]")){
                            queueLength = 0;
                        } else {
                            queueLength = queueParticipantsListModel.getParticipants().size();
                        }
                        initRecyclerView(fragment, queueLength);
                        addDocumentSnapshot(queueParticipantsListModel.getId(), fragment, queueParticipantsListModel.getParticipants());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    private void addDocumentSnapshot(String queueID, Fragment fragment, List<String> participants) {
        DI.addDocumentSnapShot.invoke(queueID, participants)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<QueueSizeModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull QueueSizeModel queueSizeModel) {
                        List<DelegateItem> newItems = new ArrayList<>();
                        newItems.addAll(_items.getValue());
                        newItems.add(new ParticipantListDelegateItem(new ParticipantListModel(3, fragment.getString(R.string.participant) + "#" + queueSizeModel.getSize())));
                        _items.setValue(newItems);
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


