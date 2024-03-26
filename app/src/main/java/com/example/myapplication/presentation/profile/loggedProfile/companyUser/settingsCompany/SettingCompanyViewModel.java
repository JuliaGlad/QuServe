package com.example.myapplication.presentation.profile.loggedProfile.companyUser.settingsCompany;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.model.CompanyUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.state.CompanyUserState;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class SettingCompanyViewModel extends ViewModel {

    private final MutableLiveData<CompanyUserState> _state = new MutableLiveData<>(new CompanyUserState.Loading());
    LiveData<CompanyUserState> state = _state;

    public void getCompany(String companyId) {

        DI.getSingleCompanyUseCase.invoke(companyId)
                .zipWith(DI.getCompanyLogoUseCase.invoke(companyId), (companyNameAndEmailModel, imageModel) ->
                        new CompanyUserModel(companyNameAndEmailModel.getName(), companyNameAndEmailModel.getEmail(), imageModel.getImageUri()))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyUserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyUserModel companyUserModel) {
                        _state.postValue(new CompanyUserState.Success(companyUserModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}