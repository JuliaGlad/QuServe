package com.example.myapplication.domain.usecase.companyQueue.company;

import static com.example.myapplication.DI.service;

import com.example.myapplication.DI;
import com.example.myapplication.data.dto.CompanyDto;
import com.example.myapplication.domain.model.company.CompanyModel;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyUseCase {
    public Single<List<CompanyNameIdModel>> invoke() {
        return DI.companyUserRepository.getCompany().map(companyDtos -> companyDtos
                .stream()
                .map(companyDto -> new CompanyNameIdModel(companyDto.getId(), companyDto.getCompanyName(), companyDto.getUri()))
                .collect(Collectors.toList()));
    }
}
