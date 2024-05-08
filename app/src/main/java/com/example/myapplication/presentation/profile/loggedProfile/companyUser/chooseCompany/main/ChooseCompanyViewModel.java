package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CommonCompanyDI;
import com.example.myapplication.domain.model.common.CommonCompanyModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState.ChooseCompanyState;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState.CompanyListModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState.ListItemModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChooseCompanyViewModel extends ViewModel {

    private final MutableLiveData<ChooseCompanyState> _state = new MutableLiveData<>(new ChooseCompanyState.Loading());
    LiveData<ChooseCompanyState> state = _state;

    public void getAllCompaniesList(){
        CommonCompanyDI.getAllServiceCompaniesUseCase.invoke()
                .zipWith(CommonCompanyDI.getAllCompaniesLogosUseCase.invoke(), (commonCompanyModels, imageTaskModels) -> {
                    List<CompanyListModel> models = new ArrayList<>();
                    for (CommonCompanyModel current:
                        commonCompanyModels ) {
                        models.add(new CompanyListModel(
                                current.getCompanyId(),
                                current.getService(),
                                current.getCompanyName()
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