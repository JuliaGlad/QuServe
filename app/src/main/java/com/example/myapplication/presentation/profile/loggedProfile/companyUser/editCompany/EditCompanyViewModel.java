package com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.company.CompanyModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditCompanyViewModel extends ViewModel {

    private final MutableLiveData<String> _companyName = new MutableLiveData<>();
    LiveData<String> companyName = _companyName;

    private final MutableLiveData<String> _companyPhone = new MutableLiveData<>();
    LiveData<String> companyPhone = _companyPhone;

    private final MutableLiveData<String> _companyEmail = new MutableLiveData<>();
    LiveData<String> companyEmail = _companyEmail;

    private final MutableLiveData<String> _companyService = new MutableLiveData<>();
    LiveData<String> companyService = _companyService;

    private final MutableLiveData<Uri> _uri = new MutableLiveData<>();
    LiveData<Uri> uri = _uri;

    private final MutableLiveData<Boolean> _navigateBack = new MutableLiveData<>();
    LiveData<Boolean> navigateBack = _navigateBack;

    public void getCompanyData(String companyId) {
        DI.getCompanyModelUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyModel companyModel) {
                        _companyName.postValue(companyModel.getName());
                        _companyEmail.postValue(companyModel.getEmail());
                        _companyPhone.postValue(companyModel.getPhone());
                        _companyService.postValue(companyModel.getService());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getCompanyLogo(String companyId) {
        DI.getCompanyLogoUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ImageModel imageModel) {
                        _uri.postValue(imageModel.getImageUri());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void updateData(String companyId, String name, String phone, Uri uri) {
        DI.updateCompanyDataUseCase.invoke(companyId, name, phone)
                .concatWith(DI.uploadCompanyLogoToFireStorageUseCase.invoke(uri, companyId))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _navigateBack.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

}