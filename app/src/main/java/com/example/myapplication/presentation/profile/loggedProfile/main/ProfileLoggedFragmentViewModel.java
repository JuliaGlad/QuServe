package com.example.myapplication.presentation.profile.loggedProfile.main;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileLoggedFragmentViewModel extends ViewModel {

    public boolean checkAuth(){
        return DI.checkAuthentificationUseCase.invoke();
    }

    public boolean isNull() {
        return DI.isNullUseCase.invoke();
    }


}
