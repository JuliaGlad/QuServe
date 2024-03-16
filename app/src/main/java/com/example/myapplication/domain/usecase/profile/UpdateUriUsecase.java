package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateUriUsecase {

    public Completable invoke(String uri){
        return DI.profileRepository.updateUri(uri);
    }
}
