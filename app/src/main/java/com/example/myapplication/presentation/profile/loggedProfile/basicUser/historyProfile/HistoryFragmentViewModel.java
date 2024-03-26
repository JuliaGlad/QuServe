package com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.profile.HistoryModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.models.HistoryItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.models.HistoryState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HistoryFragmentViewModel extends ViewModel{

    private final MutableLiveData<HistoryState> _state = new MutableLiveData<>(new HistoryState.Loading());
    LiveData<HistoryState> state = _state;

    public void getHistoryData(){
        DI.getHistoryUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<HistoryModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<HistoryModel> historyModels) {
                        List<HistoryItemModel> models = new ArrayList<>();
                        for (int i = 0; i < historyModels.size(); i++) {
                            models.add(new HistoryItemModel(
                                    historyModels.get(i).getDate(),
                                    historyModels.get(i).getTime(),
                                    historyModels.get(i).getName()
                            ));
                        }
                        _state.postValue(new HistoryState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}
