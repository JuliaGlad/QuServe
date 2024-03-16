package com.example.myapplication.presentation.profile.loggedProfile.companyUser;

import static com.example.myapplication.presentation.utils.Utils.COMPANY;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.common.ImageTaskModel;
import com.example.myapplication.domain.model.company.CompanyNameAndEmailModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.companyUserModelAndState.CompanyUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemModel;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CompanyUserViewModel extends ViewModel {

    private String name, email;
    private Uri uri;

    private final MutableLiveData<Boolean> _openEditActivity = new MutableLiveData<>(false);
    LiveData<Boolean> openEditActivity = _openEditActivity;

    private final MutableLiveData<Boolean> _isExist = new MutableLiveData<>(true);
    LiveData<Boolean> isExist = _isExist;

    private final MutableLiveData<Boolean> _openEmployeesActivity = new MutableLiveData<>(false);
    LiveData<Boolean> openEmployeesActivity = _openEmployeesActivity;

    private final MutableLiveData<Boolean> _goToProfile = new MutableLiveData<>(false);
    LiveData<Boolean> goToProfile = _goToProfile;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> items = _items;

    public void initRecycler(String companyId) {
        buildList(new DelegateItem[]{
                new MainItemDelegateItem(new MainItemModel(0, uri, name, email, COMPANY, companyId)),
                new ServiceItemDelegateItem(new ServiceItemModel(1, R.drawable.ic_edit, R.string.edit_company, () -> {
                    _openEditActivity.postValue(true);
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_group, R.string.employees, () -> {
                    _openEmployeesActivity.postValue(true);
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_person_filled_24, R.string.go_to_my_profile, () -> {
                    _goToProfile.postValue(true);
                }))
        });
    }

    public void setLiveDataToDefault() {
        _openEmployeesActivity.postValue(false);
        _openEditActivity.postValue(false);
        _goToProfile.postValue(false);
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

                        initRecycler(companyId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    private void buildList(DelegateItem[] item) {
        List<DelegateItem> list = Arrays.asList(item);
        _items.postValue(list);
    }
}