package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class CheckCurrentCompanyExist {

    public boolean invoke(String companyId) {
        Single<CompanyModel> single = DI.companyUserRepository.getCompany().flatMap(companyDtos ->
                Single.just(Objects.requireNonNull(companyDtos
                        .stream()
                        .filter(companyDto -> companyDto.getId().equals(companyId))
                        .map(companyDto -> new CompanyModel(companyDto.getCompanyName(), companyDto.getCompanyEmail(), companyDto.getCompanyPhone(), companyDto.getCompanyService()))
                        .findFirst()
                        .orElse(null))));
        return single != null;
    }
}
