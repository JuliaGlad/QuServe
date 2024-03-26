package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState.ChooseCompanyState;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState.CompanyListModel;
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

    private final MutableLiveData<ChooseCompanyState> _state = new MutableLiveData<>(new ChooseCompanyState.Loading());
    LiveData<ChooseCompanyState> state = _state;

    public void getCompaniesList() {
        DI.getCompanyUseCase.invoke()
                        .zipWith(DI.getCompaniesLogosUseCase.invoke(), (companyNameIdModels, imageTaskModels) -> {
                            List<CompanyListModel> models = new ArrayList<>();
                            for (int i = 0; i < companyNameIdModels.size(); i++) {
                                models.add(new CompanyListModel(
                                        companyNameIdModels.get(i).getId(),
                                        companyNameIdModels.get(i).getName(),
                                        companyNameIdModels.get(i).getUri()
                                ));
                            }
                            return new ListItemModel(models, imageTaskModels);
                        })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ListItemModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ListItemModel listItemModel) {
                        _state.postValue(new ChooseCompanyState.Success(listItemModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}