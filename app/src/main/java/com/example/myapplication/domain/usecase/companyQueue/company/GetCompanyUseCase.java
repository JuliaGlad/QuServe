package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyUseCase {
    public Single<List<CompanyNameIdModel>> invoke() {
        List<CompanyNameIdModel> list = new ArrayList<>();
        return DI.companyUserRepository.getCompany().map(companyDtos -> {
            for (int i = 0; i < companyDtos.size(); i++) {
                list.add(new CompanyNameIdModel(companyDtos.get(i).getId(), companyDtos.get(i).getCompanyName(), companyDtos.get(i).getUri()));
            }
            return list;
        });
    }
}
