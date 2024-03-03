package com.example.myapplication.presentation.profile.loggedProfile.chooseCompany;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;
import com.example.myapplication.presentation.profile.loggedProfile.chooseCompany.companyDelegate.CompanyDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.chooseCompany.companyDelegate.CompanyModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.FloatingActionButtonItemListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonModel;

public class ChooseCompanyViewModel extends ViewModel {

    List<CompanyNameIdModel> list = new ArrayList<>();

    private final MutableLiveData<String> _navigate = new MutableLiveData<>(null);
    LiveData<String> navigate = _navigate;

    private final MutableLiveData<List<DelegateItem>> _item = new MutableLiveData<>();
    LiveData<List<DelegateItem>> item = _item;

    private final MutableLiveData<Boolean> _openCreateActivity = new MutableLiveData<>();
    LiveData<Boolean> openCreateActivity = _openCreateActivity;

    private final List<DelegateItem> itemsList = new ArrayList<>();

    private void initRecycler(){
            setItems();
            _item.postValue(itemsList);
    }

    private void setItems(){
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getId();
            itemsList.add(new CompanyDelegateItem(new CompanyModel(i, list.get(i).getName(), list.get(i).getId(), () -> {
                _navigate.postValue(id);
            })));
        }
        itemsList.add(new FloatingActionButtonDelegateItem(new FloatingActionButtonModel(2, () -> {
            _openCreateActivity.postValue(true);
        })));
    }

    public void getCompaniesList(){
        DI.getCompanyUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<CompanyNameIdModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CompanyNameIdModel> companyNameIdModels) {
                        list = companyNameIdModels;
                        initRecycler();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}