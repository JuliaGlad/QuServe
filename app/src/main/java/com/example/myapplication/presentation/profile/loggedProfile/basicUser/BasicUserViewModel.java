package com.example.myapplication.presentation.profile.loggedProfile.basicUser;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CommonCompanyDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel.BasicUserDataModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel.BasicUserState;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel.BooleanData;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class BasicUserViewModel extends ViewModel {

    private final MutableLiveData<String> _companyId = new MutableLiveData<>(null);
    LiveData<String> companyId = _companyId;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> item = _items;

    private final MutableLiveData<Boolean> _companyExist = new MutableLiveData<>(false);
    LiveData<Boolean> companyExist = _companyExist;

    private final MutableLiveData<Boolean> _checked = new MutableLiveData<>(false);
    LiveData<Boolean> checked = _checked;

    private final MutableLiveData<Uri> _uri = new MutableLiveData<>();
    LiveData<Uri> uri = _uri;

    private final MutableLiveData<BasicUserState> _state = new MutableLiveData<>(new BasicUserState.Loading());
    LiveData<BasicUserState> state = _state;

    public void retrieveUserNameData() {
        Single.zip(ProfileDI.getProfileImageUseCase.invoke(), ProfileDI.getUserEmailAndNameDataUseCase.invoke(), CommonCompanyDI.checkAnyCompanyExistUseCase.invoke(),
                        (imageModel, userEmailAndNameModel, aBoolean) ->
                                new BasicUserDataModel(
                                        userEmailAndNameModel.getName(),
                                        userEmailAndNameModel.getEmail(),
                                        imageModel.getImageUri(),
                                        aBoolean)
                )
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<BasicUserDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull BasicUserDataModel data) {
                        _companyExist.postValue(data.isaBoolean());
                        _state.postValue(new BasicUserState.Success(new BasicUserDataModel(
                                data.getName(),
                                data.getEmail(),
                                data.getUri(),
                                data.isaBoolean()
                        )
                        ));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("On error basic user", "errror");
                        _state.postValue(new BasicUserState.Error());
                    }
                });
    }

    public void uploadToFireStorage(Uri uri) {
        ProfileDI.uploadBackgroundImageUseCase.invoke(uri)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Complete", "image uploaded");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("Error", e.getMessage());
                    }
                });
    }

    // if (!aBoolean) {
//        return null;
//    } else {
//        return new BooleanData(true, companyNameIdModels);
//    }
    public void isCompanyExist() {
        CommonCompanyDI.checkAnyCompanyExistUseCase.invoke()
                .flatMap(aBoolean -> CommonCompanyDI.getAllServiceCompaniesUseCase.invoke(), (aBoolean, commonCompanyModels) -> {
                    if (!aBoolean) {
                        return null;
                    } else {
                        return new BooleanData(true, commonCompanyModels);
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<BooleanData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull BooleanData booleanData) {
                        if (booleanData != null) {
                            if (booleanData.getList().size() == 1) {
                                _companyId.postValue(booleanData.getList().get(0).getCompanyId());
                                _checked.postValue(true);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public void setBackground() {
        ProfileDI.getBackgroundImageUseCase.invoke()
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
}
