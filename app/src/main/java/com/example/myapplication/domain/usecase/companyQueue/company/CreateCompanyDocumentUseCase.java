package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class CreateCompanyDocumentUseCase {
    public Completable invoke(String companyID, String name, String email, String phone, String companyService){
       return DI.companyQueueUserRepository.createCompanyQueueDocument(companyID, name, email, phone, companyService);
    }
}
