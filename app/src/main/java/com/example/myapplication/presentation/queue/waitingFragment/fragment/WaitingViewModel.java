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
import com.example.myapplication.domain.model.QueueModel;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.leaveQueueItem.LeaveQueueDelegateItem;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.leaveQueueItem.LeaveQueueModel;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem.WaitingItemDelegateItem;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem.WaitingItemModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewModel;

public class WaitingViewModel extends ViewModel {

    private String queueID;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> items = _items;

    private final MutableLiveData<Boolean> _showLeaveDialog = new MutableLiveData<>();
    public LiveData<Boolean> showLeaveDialog = _showLeaveDialog;

    private void initRecycler(String queueId, List<String> list, String queueName, String time, String queueLength, Fragment fragment) {
        buildList(new DelegateItem[]{
                new StringTextViewDelegateItem(new StringTextViewModel(1, queueName, 28, View.TEXT_ALIGNMENT_CENTER)),
                new WaitingItemDelegateItem(new WaitingItemModel(2, queueId, list, fragment.getString(R.string.estimated_waiting_time), time, true, EDIT_ESTIMATED_TIME)),
                new WaitingItemDelegateItem(new WaitingItemModel(3, queueId, list, fragment.getString(R.string.people_before_you), queueLength, true, EDIT_PEOPLE_BEFORE_YOU)),
                new WaitingItemDelegateItem(new WaitingItemModel(4, queueId, list, fragment.getString(R.string.useful_tips), fragment.getString(R.string.tips_description), false, null)),
                new LeaveQueueDelegateItem(new LeaveQueueModel(4, () -> {
                    _showLeaveDialog.postValue(true);
                }))
        });
    }

    public Completable leaveQueue() {
        return DI.removeParticipantById.invoke(queueID);
    }

    public void getQueue(Fragment fragment) {
        DI.getQueueByParticipantIdUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueModel queueModel) {
                        List<String> participants = Arrays.asList(queueModel.getParticipants().toString().split(","));
                        initRecycler(queueModel.getId(), participants, queueModel.getName(), "12 minutes", String.valueOf(participants.size()), fragment);
                        queueID = queueModel.getId();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Exception", e.toString());
                    }
                });
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = new ArrayList<>(Arrays.asList(items));
        _items.setValue(list);
    }
}