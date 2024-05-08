package com.example.myapplication.presentation.dialogFragments.deleteWorkerFromQueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeleteWorkerFromQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isDeleted = new MutableLiveData<>(false);
    LiveData<Boolean> isDeleted = _isDeleted;

    public void deleteFromQueue(String companyId, String queueId, String employeeId){
        CompanyQueueDI.deleteEmployeeFromQueueUseCase.invoke(companyId, queueId, employeeId)
                .concatWith(ProfileEmployeeDI.deleteActiveQueueUseCase.invoke(companyId, queueId, employeeId))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isDeleted.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}
