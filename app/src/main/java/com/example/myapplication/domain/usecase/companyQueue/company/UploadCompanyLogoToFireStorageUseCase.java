package com.example.myapplication.domain.usecase.companyQueue.company;

import android.net.Uri;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadCompanyLogoToFireStorageUseCase {

    public Completable invoke(Uri uri, String companyId){
        return DI.companyUserRepository.uploadCompanyLogoToFireStorage(uri, companyId);
    }

}
