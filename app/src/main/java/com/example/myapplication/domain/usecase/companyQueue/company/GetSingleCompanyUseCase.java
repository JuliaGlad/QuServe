package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.company.CompanyNameAndEmailModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetSingleCompanyUseCase {
    public Single<CompanyNameAndEmailModel> invoke(String companyId) {
        return DI.companyQueueUserRepository.getCompanyQueue().flatMap(companyDtos ->
                Single.just(Objects.requireNonNull(companyDtos
                        .stream()
                        .filter(companyDto -> companyDto.getId().equals(companyId))
                        .map(companyDto -> new CompanyNameAndEmailModel(companyDto.getCompanyName(), companyDto.getCompanyEmail()))
                        .findFirst()
                        .orElse(null))));
    }
}
