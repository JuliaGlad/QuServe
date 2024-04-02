package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyForWorkerModel;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyForWorkerByPathUseCase {
    public Single<CompanyForWorkerModel> invoke(String path) {
        return DI.companyUserRepository.getCompanyByStringPath(path).map(companyDto ->
                new CompanyForWorkerModel(companyDto.getId(), companyDto.getCompanyName())
        );
    }
}
