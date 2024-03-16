package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageTaskModel;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState.ListItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.companyListItem.CompanyListItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.companyListItem.CompanyListItemDelegateModel;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ChooseCompanyViewModel extends ViewModel {

    List<CompanyNameIdModel> list = new ArrayList<>();
    List<Task<Uri>> imageList = new ArrayList<>();

    private final MutableLiveData<String> _navigate = new MutableLiveData<>(null);
    LiveData<String> navigate = _navigate;

    private final MutableLiveData<List<DelegateItem>> _item = new MutableLiveData<>();
    LiveData<List<DelegateItem>> item = _item;

    private final List<DelegateItem> itemsList = new ArrayList<>();

    private void initRecycler() {
        setItems();
        _item.postValue(itemsList);
    }

    private void setItems() {
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getId();
            itemsList.add(new CompanyListItemDelegateItem(new CompanyListItemDelegateModel(
                    i,
                    list.get(i).getName(),
                    imageList.get(i),
                    () -> _navigate.postValue(id))));

        }
    }

    public void getCompaniesList() {
        DI.getCompanyUseCase.invoke()
                .zipWith(DI.getCompaniesLogosUseCase.invoke(), ListItemModel::new)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ListItemModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ListItemModel listItemModel) {
                        list = listItemModel.getCompanyNameIdModels();
                        List<ImageTaskModel> tasks = listItemModel.getImageTaskModels();
                        for (int i = 0; i < tasks.size(); i++) {
                            imageList.add(tasks.get(i).getTask());
                        }
                        initRecycler();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}