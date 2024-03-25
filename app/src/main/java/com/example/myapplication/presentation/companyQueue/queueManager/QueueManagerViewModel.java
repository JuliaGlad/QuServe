package com.example.myapplication.presentation.companyQueue.queueManager;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyQueueManagerModel;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.companyQueue.queueManager.recycler_view.ManagerItemModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.ButtonItemListener;

public class QueueManagerViewModel extends ViewModel {

    private final List<ManagerItemModel> models = new ArrayList<>();

    private final MutableLiveData<List<ManagerItemModel>> _items = new MutableLiveData<>();
    LiveData<List<ManagerItemModel>> items = _items;

    public void getList(String companyId, Fragment fragment) {
        DI.getCompaniesQueuesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<CompanyQueueManagerModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CompanyQueueManagerModel> companyQueueManagerModels) {
                        for (int i = 0; i < companyQueueManagerModels.size(); i++) {
                            String queueId = companyQueueManagerModels.get(i).getId();
                            models.add(new ManagerItemModel(
                                    i,
                                    queueId,
                                    companyQueueManagerModels.get(i).getName(),
                                    companyQueueManagerModels.get(i).getWorkersCount(),
                                    companyQueueManagerModels.get(i).getLocation(),
                                    companyQueueManagerModels.get(i).getCity(),
                                    () -> {
                                        ((QueueManagerActivity)fragment.requireActivity()).openQueueDetails(companyId, queueId);
                                    })
                            );
                        }
                        _items.postValue(models);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}