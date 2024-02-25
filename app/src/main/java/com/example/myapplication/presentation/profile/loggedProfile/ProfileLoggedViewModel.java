package com.example.myapplication.presentation.profile.loggedProfile;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.USER_NAME_KEY;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.DI;
import com.example.myapplication.domain.model.ImageModel;
import com.example.myapplication.domain.model.UserEmailAndNameModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileLoggedViewModel extends ViewModel {

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    LiveData<Task<Uri>> image = _image;

    private final MutableLiveData<Task<Uri>> _imageUpdated = new MutableLiveData<>();
    LiveData<Task<Uri>> imageUpdated = _imageUpdated;

    private final MutableLiveData<String> _userName = new MutableLiveData<>();
    LiveData<String> userName = _userName;

    private final MutableLiveData<String> _userEmail = new MutableLiveData<>();
    LiveData<String> userEmail = _userEmail;

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
        DI.getUserEmailAndPasswordDataUseCase.invoke()
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
