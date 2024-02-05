package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.JpgImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetProfileImageUseCase {
    public Single<JpgImageModel> invoke(){
        return DI.profileRepository.getProfileImage().flatMap(uri ->
                Single.just(new JpgImageModel(uri.getImageUri()))
        );
    }
}
