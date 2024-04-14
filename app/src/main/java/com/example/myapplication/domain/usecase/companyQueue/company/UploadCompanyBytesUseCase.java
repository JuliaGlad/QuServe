package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadCompanyBytesUseCase {
    public Completable invoke(String companyId, byte[] data){
       return DI.companyQueueUserRepository.uploadCompanyQueueBytesToFireStorage(companyId, data);
    }
}
