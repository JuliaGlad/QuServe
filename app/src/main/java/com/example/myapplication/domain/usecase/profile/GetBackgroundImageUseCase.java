package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetBackgroundImageUseCase {
    public Single<ImageModel> invoke(){
        return DI.profileRepository.getBackgroundImage().flatMap(imageDto ->
                Single.just(new ImageModel(imageDto.getImageUri()))
        );
    }
}
