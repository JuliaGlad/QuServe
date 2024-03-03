package com.example.myapplication.presentation.profile.loggedProfile.basicUser;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.profile.UserEmailAndNameModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BasicUserViewModel extends ViewModel {
    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    LiveData<Task<Uri>> image = _image;

    private final MutableLiveData<Task<Uri>> _imageUpdated = new MutableLiveData<>();
    LiveData<Task<Uri>> imageUpdated = _imageUpdated;

    private final MutableLiveData<String> _userName = new MutableLiveData<>();
    LiveData<String> userName = _userName;

    private final MutableLiveData<String> _userEmail = new MutableLiveData<>();
    LiveData<String> userEmail = _userEmail;

    private final MutableLiveData<Boolean> _companyExist = new MutableLiveData<>();
    LiveData<Boolean> companyExist = _companyExist;

    public void checkCompanyExist(){
        DI.checkCompanyExistUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                       _companyExist.postValue(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void addSnapshot() {
        DI.addSnapshotProfileUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DocumentSnapshot snapshot) {
                        _userName.postValue(DI.getNameUseCase.invoke(snapshot));
                        getProfileImage();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void retrieveUserNameData() {
        DI.getUserEmailAndNameDataUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserEmailAndNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UserEmailAndNameModel userEmailAndNameModel) {
                        _userName.postValue(userEmailAndNameModel.getName());
                        _userEmail.postValue(userEmailAndNameModel.getEmail());
                        addSnapshot();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    public void getProfileImage() {
        DI.getProfileImageUseCase.invoke()
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
}