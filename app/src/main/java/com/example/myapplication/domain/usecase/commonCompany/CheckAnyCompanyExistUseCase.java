package com.example.myapplication.domain.usecase.commonCompany;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Single;

public class CheckAnyCompanyExistUseCase {
    public Single<Boolean> invoke(){
        return DI.commonCompanyRepository.checkCompanyExist();
    }
}
