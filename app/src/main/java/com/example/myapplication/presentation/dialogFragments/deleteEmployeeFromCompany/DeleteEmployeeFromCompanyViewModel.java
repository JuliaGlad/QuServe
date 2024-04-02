package com.example.myapplication.presentation.dialogFragments.deleteEmployeeFromCompany;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeleteEmployeeFromCompanyViewModel extends ViewModel {

    String roleString = null;

    private final MutableLiveData<String> _role = new MutableLiveData<>(null);
    LiveData<String> role = _role;

    public void deleteEmployee(String employeeId, String companyId){
       DI.deleteEmployeeFromCompanyUseCase.invoke(employeeId, companyId)
               .flatMapCompletable(string -> {
                   roleString = string;
                   return DI.deleteEmployeeFromAllQueuesUseCase.invoke(companyId, employeeId);
               })
               .andThen(DI.deleteEmployeeRoleUseCase.invoke(companyId, employeeId))
               .subscribeOn(Schedulers.io())
               .subscribe(new CompletableObserver() {
                   @Override
                   public void onSubscribe(@NonNull Disposable d) {

                   }

                   @Override
                   public void onComplete() {
                       _role.postValue(roleString);
                   }

                   @Override
                   public void onError(@NonNull Throwable e) {

                   }
               });

    }
}
