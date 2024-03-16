package com.example.myapplication.presentation.profile.loggedProfile.companyUser.settingsCompany;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.company.CompanyNameAndEmailModel;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.companyUserModelAndState.CompanyUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate.SettingsUserItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate.SettingsUserItemModel;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class SettingCompanyViewModel extends ViewModel {

    private String name, email;
    private Uri uri;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> items = _items;

    private final MutableLiveData<Boolean> _openHelpDialog = new MutableLiveData<>();
    LiveData<Boolean> openHelpDialog = _openHelpDialog;

    private final MutableLiveData<Boolean> _openAboutUsDialog = new MutableLiveData<>(false);
    LiveData<Boolean> openAboutUsDialog = _openAboutUsDialog;

    private final MutableLiveData<Boolean> _openDeleteDialog = new MutableLiveData<>(false);
    LiveData<Boolean> openDeleteDialog = _openDeleteDialog;

    private void initRecycler() {
        buildList(new DelegateItem[]{
                new SettingsUserItemDelegateItem(new SettingsUserItemModel(0, name, email, uri)),
                new ServiceItemDelegateItem(new ServiceItemModel(1, R.drawable.ic_shield_person, R.string.change_owner, () -> {

                })),
                new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_help, R.string.help, () -> {
                    _openHelpDialog.postValue(true);
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_group, R.string.about_us, () -> {
                    _openAboutUsDialog.postValue(true);
                })),
                new ServiceRedItemDelegateItem(new ServiceRedItemModel(3, R.drawable.ic_delete, R.string.delete_company, () -> {
                    _openDeleteDialog.postValue(true);
                }))
        });
    }

    public void getCompany(String companyId) {

        DI.getSingleCompanyUseCase.invoke(companyId)
                .zipWith(DI.getCompanyLogoUseCase.invoke(companyId), (companyNameAndEmailModel, imageModel) ->
                        new CompanyUserModel(companyNameAndEmailModel.getName(), companyNameAndEmailModel.getEmail(), imageModel.getImageUri()))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyUserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyUserModel companyUserModel) {
                        name = companyUserModel.getName();
                        email = companyUserModel.getEmail();
                        uri = companyUserModel.getUri();
                        initRecycler();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
//        DI.getSingleCompanyUseCase.invoke(companyId)
//                .subscribeOn(Schedulers.io())
//                .subscribe(new SingleObserver<CompanyNameAndEmailModel>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(@NonNull CompanyNameAndEmailModel companyNameAndEmailModel) {
//                        name = companyNameAndEmailModel.getName();
//                        email = companyNameAndEmailModel.getEmail();
//
//                        DI.getCompanyLogoUseCase.invoke(companyId)
//                                .subscribeOn(Schedulers.io())
//                                .subscribe(new SingleObserver<ImageModel>() {
//                                    @Override
//                                    public void onSubscribe(@NonNull Disposable d) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(@NonNull ImageModel imageModel) {
//                                        uri = imageModel.getImageUri();
//                                        initRecycler();
//                                    }
//
//                                    @Override
//                                    public void onError(@NonNull Throwable e) {
//
//                                    }
//                                });
//
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//                });
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = Arrays.asList(items);
        _items.postValue(list);
    }

}