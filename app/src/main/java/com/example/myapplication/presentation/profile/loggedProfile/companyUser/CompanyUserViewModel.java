package com.example.myapplication.presentation.profile.loggedProfile.companyUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;
import com.example.myapplication.domain.model.company.CompanyNameModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CompanyUserViewModel extends ViewModel {

    private final MutableLiveData<String> _companyName = new MutableLiveData<>();
    LiveData<String> companyName = _companyName;

    public void getQueue(String companyId) {
        DI.getSingleCompanyUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyNameModel companyNameModel) {
                        _companyName.postValue(companyNameModel.getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}