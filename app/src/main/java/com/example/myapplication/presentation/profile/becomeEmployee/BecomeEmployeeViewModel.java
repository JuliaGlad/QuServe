package com.example.myapplication.presentation.profile.becomeEmployee;

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

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    LiveData<Task<Uri>> image = _image;

    private final MutableLiveData<String> _companyName = new MutableLiveData<>();
    LiveData<String> companyName = _companyName;

    public void getCompany(String path) {
        DI.getCompanyByStringPathUseCase.invoke(path)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyNameIdModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyNameIdModel companyNameIdModel) {
                        _companyName.postValue(companyNameIdModel.getName());
                        getQrCode(companyNameIdModel.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void getQrCode(String companyId){
        DI.getEmployeeQrCodeUseCase.invoke(companyId)
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

    public void addEmployee(String path){

        DI.getUserEmailAndNameDataUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserEmailAndNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UserEmailAndNameModel userEmailAndNameModel) {
                        DI.addEmployeeUseCase.invoke(path, userEmailAndNameModel.getEmail(), userEmailAndNameModel.getName())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.d("Added", "user added");
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
}