package com.example.myapplication.presentation.dialogFragments.changeRole;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChangeRoleDialogViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _dismiss = new MutableLiveData<>(false);
    LiveData<Boolean> dismiss = _dismiss;

    public void updateField(String role, String employeeId, String companyId) {
        DI.updateRoleUseCase.invoke(role, employeeId, companyId)
                .concatWith(DI.updateEmployeeRoleUseCase.invoke(companyId, employeeId, role))
                .andThen(DI.removeAdminFromAllQueuesAsWorkerUseCase.invoke(companyId, employeeId, role))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _dismiss.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}