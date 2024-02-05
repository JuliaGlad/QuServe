package com.example.myapplication.domain.usecase.profile;

import android.net.Uri;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadToFireStorageUseCase {
    public Completable invoke(Uri imageUri){
        return DI.profileRepository.uploadToFireStorage(imageUri);
    }

}
