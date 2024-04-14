package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.company.EmployeeRoleCompanyModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetEmployeeRoleCompanyUseCaseByEmployee {
    public Single<List<EmployeeRoleCompanyModel>> invoke(List<String> ids){
        return DI.companyQueueUserRepository.getCompanyQueue().map(companyDtos -> companyDtos
                .stream()
                .filter(companyDto -> ids.contains(companyDto.getId()))
                .map(companyDto -> new EmployeeRoleCompanyModel(companyDto.getId(), companyDto.getCompanyName()))
                .collect(Collectors.toList()));
    }
}
