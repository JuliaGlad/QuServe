package com.example.myapplication.domain.usecase.profile.images;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetBackgroundImageUseCase {
    public Single<ImageModel> invoke(){
        return ProfileDI.profileImages.getBackgroundImage().flatMap(imageDto ->
                Single.just(new ImageModel(imageDto.getImageUri()))
        );
    }
}
