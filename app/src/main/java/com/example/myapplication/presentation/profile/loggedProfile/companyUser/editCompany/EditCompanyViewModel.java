package com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.company.CompanyModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany.model.EditCompanyModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany.state.EditCompanyState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditCompanyViewModel extends ViewModel {

    private final MutableLiveData<EditCompanyState> _state = new MutableLiveData<>(new EditCompanyState.Loading());
    LiveData<EditCompanyState> state = _state;

    private final MutableLiveData<Boolean> _navigateBack = new MutableLiveData<>();
    LiveData<Boolean> navigateBack = _navigateBack;

    public void getCompanyData(String companyId) {
        DI.getCompanyModelUseCase.invoke(companyId)
                .zipWith(DI.getCompanyLogoUseCase.invoke(companyId), (companyModel, imageModel) -> new EditCompanyModel(
                        companyModel.getName(),
                        companyModel.getEmail(),
                        companyModel.getPhone(),
                        companyModel.getService(),
                        imageModel.getImageUri()
                ))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<EditCompanyModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull EditCompanyModel editCompanyModel) {
                        _state.postValue(new EditCompanyState.Success(editCompanyModel));
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