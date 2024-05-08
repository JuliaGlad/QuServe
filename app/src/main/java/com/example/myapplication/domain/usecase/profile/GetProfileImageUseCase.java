package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetProfileImageUseCase {
    public Single<ImageModel> invoke(){
        return ProfileDI.profileImages.getProfileImage().flatMap(uri ->
                Single.just(new ImageModel(uri.getImageUri()))
        );
    }
}
