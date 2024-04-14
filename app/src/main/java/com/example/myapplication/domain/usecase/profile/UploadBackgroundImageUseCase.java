package com.example.myapplication.domain.usecase.profile;

import android.net.Uri;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadBackgroundImageUseCase {
    public Completable invoke(Uri uri){
        return DI.profileRepository.uploadBackgroundToFireStorage(uri);
    }
}
