package com.example.myapplication.domain.usecase.profile.images;

import android.net.Uri;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Completable;

public class UploadBackgroundImageUseCase {
    public Completable invoke(Uri uri){
        return ProfileDI.profileImages.uploadBackgroundToFireStorage(uri);
    }
}
