package com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.profile.HistoryModel;
import com.example.myapplication.domain.model.profile.HistoryQueueModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.date.DateDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.date.DateModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.history.HistoryDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.history.HistoryDelegateModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HistoryFragmentViewModel extends ViewModel{

    private final List<DelegateItem> delegates = new ArrayList<>();
    private final List<String> dates = new ArrayList<>();

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> items = _items;

    public void getHistoryData(){
        DI.getHistoryUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<HistoryModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<HistoryModel> historyModels) {
                        for (int i = 0; i < historyModels.size(); i++) {
                            String date = historyModels.get(i).getDate();
                            if (!dates.contains(date))
                                dates.add(date);
                        }

                        for (int i = 0; i < dates.size(); i++) {
                            delegates.add(new DateDelegateItem(new DateModel(i, dates.get(i))));
                            for (int j = 0; j < historyModels.size(); j++) {
                                if (dates.get(i).equals(historyModels.get(j).getDate())){
                                    delegates.add(new HistoryDelegateItem(new HistoryDelegateModel(
                                            j,
                                            historyModels.get(j).getName(),
                                            historyModels.get(j).getTime()
                                    )));
                                }
                            }
                        }

                        initRecycler(delegates);
                        Log.d("Delegates", String.valueOf(delegates.size()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void initRecycler(List<DelegateItem> list){
        _items.postValue(list);
    }

    private void buildList(DelegateItem[] items){
        List<DelegateItem> list = Arrays.asList(items);
        _items.postValue(list);
    }

}
