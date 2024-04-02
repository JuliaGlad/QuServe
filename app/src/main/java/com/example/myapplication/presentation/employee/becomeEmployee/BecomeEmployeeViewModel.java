package com.example.myapplication.presentation.employee.becomeEmployee;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;
import com.example.myapplication.domain.model.profile.UserEmailAndNameModel;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BecomeEmployeeViewModel extends ViewModel {

    String name = null;

    private final MutableLiveData<Uri> _image = new MutableLiveData<>();
    LiveData<Uri> image = _image;

    private final MutableLiveData<String> _companyId = new MutableLiveData<>(null);
    LiveData<String> companyId = _companyId;

    private final MutableLiveData<String> _companyName = new MutableLiveData<>();
    LiveData<String> companyName = _companyName;

    private final MutableLiveData<Boolean> _isEmployee = new MutableLiveData<>(false);
    LiveData<Boolean> isEmployee = _isEmployee;

    public void getCompany(String path) {
        DI.getCompanyByStringPathUseCase.invoke(path)
                .flatMap(companyNameIdModel -> {
                    _companyName.postValue(companyNameIdModel.getName());
                    _companyId.postValue(companyNameIdModel.getId());
                    name = companyNameIdModel.getName();
                    return DI.getEmployeeQrCodeUseCase.invoke(companyNameIdModel.getId());
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

    public void addEmployee(String path, String companyId) {
        DI.getUserEmailAndNameDataUseCase.invoke()
                .flatMapCompletable(userEmailAndNameModel ->
                        DI.addEmployeeUseCase.invoke(path, userEmailAndNameModel.getEmail(), userEmailAndNameModel.getName()))
                .andThen(DI.addEmployeeRoleUseCase.invoke(companyId, path))
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