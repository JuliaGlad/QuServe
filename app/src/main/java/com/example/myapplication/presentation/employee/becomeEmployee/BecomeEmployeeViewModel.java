package com.example.myapplication.presentation.employee.becomeEmployee;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.CompanyQueueUserDI;
import com.example.myapplication.di.DI;
import com.example.myapplication.di.ProfileDI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BecomeEmployeeViewModel extends ViewModel {

    String name = null;

    private final MutableLiveData<Uri> _image = new MutableLiveData<>();
    LiveData<Uri> image = _image;

    private final MutableLiveData<String> _companyName = new MutableLiveData<>();
    LiveData<String> companyName = _companyName;

    private final MutableLiveData<Boolean> _isEmployee = new MutableLiveData<>(false);
    LiveData<Boolean> isEmployee = _isEmployee;

    public void getCompany(String companyId) {
        CompanyQueueUserDI.getSingleCompanyUseCase.invoke(companyId)
                .flatMap(companyNameAndEmailModel -> {
                    _companyName.postValue(companyNameAndEmailModel.getName());
                    return CompanyQueueUserDI.getEmployeeQrCodeUseCase.invoke(companyId);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ImageModel imageModel) {
                        _image.postValue(imageModel.getImageUri());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void addEmployee(String companyId) {
        ProfileDI.getUserEmailAndNameDataUseCase.invoke()
                .flatMapCompletable(userEmailAndNameModel ->
                        CompanyQueueUserDI.addEmployeeUseCase.invoke(companyId, userEmailAndNameModel.getName()))
                .andThen(ProfileDI.addEmployeeRoleUseCase.invoke(companyId))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isEmployee.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}