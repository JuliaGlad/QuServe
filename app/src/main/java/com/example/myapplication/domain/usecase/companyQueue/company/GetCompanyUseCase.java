package com.example.myapplication.domain.usecase.companyQueue.company;

import static com.example.myapplication.di.DI.service;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyUseCase {
    public Single<List<CompanyNameIdModel>> invoke() {
        return DI.companyQueueUserRepository.getCompanyQueue().map(companyDtos -> companyDtos
                .stream()
                .filter(documentSnapshot ->
                        Objects.equals(documentSnapshot.getOwnerId(), service.auth.getCurrentUser().getUid()))
                .map(companyDto -> new CompanyNameIdModel(companyDto.getId(), companyDto.getCompanyName(), companyDto.getUri()))
                .collect(Collectors.toList()));
    }
}
