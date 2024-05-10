package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.profile.ProfileDI;
import io.reactivex.rxjava3.core.Completable;

public class SignInWithEmailAndPasswordUseCase {

    public Completable invoke(String email, String password){
        return ProfileDI.profileRepository.signInWithEmailAndPassword(email, password);
    }

}
