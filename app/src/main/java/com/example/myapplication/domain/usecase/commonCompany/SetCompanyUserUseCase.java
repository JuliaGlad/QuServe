package com.example.myapplication.domain.usecase.commonCompany;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class SetCompanyUserUseCase {
    public Completable invoke(String companyId, String companyName, String service){
        return DI.commonCompanyRepository.setCompanyUser(companyId, companyName, service);
    }
}
