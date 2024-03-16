package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyByStringPathUseCase {
    public Single<CompanyNameIdModel> invoke(String path){
       return DI.companyUserRepository.getCompanyByStringPath(path).map(companyDto ->
               new CompanyNameIdModel(companyDto.getId(), companyDto.getCompanyName(), companyDto.getUri()));
    }
}
