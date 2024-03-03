package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyNameModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetSingleCompanyUseCase {
    public Single<CompanyNameModel> invoke(String companyId){
        return DI.companyQueueRepository.getCompany().flatMap(companyDtos ->
                Single.just(Objects.requireNonNull(companyDtos
                        .stream()
                        .filter(companyDto -> companyDto.getId().equals(companyId))
                        .map(companyDto -> new CompanyNameModel(companyDto.getCompanyName()))
                        .findFirst()
                        .orElse(null))));
    }
}
