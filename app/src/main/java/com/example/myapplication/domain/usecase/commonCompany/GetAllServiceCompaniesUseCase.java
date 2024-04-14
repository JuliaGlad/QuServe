package com.example.myapplication.domain.usecase.commonCompany;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.CommonCompanyModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetAllServiceCompaniesUseCase {
    public Single<List<CommonCompanyModel>> invoke(){
        return DI.commonCompanyRepository.getAllServiceCompanies().map(commonCompanyDtos ->
                commonCompanyDtos.stream()
                        .map(commonCompanyDto -> new CommonCompanyModel(
                                commonCompanyDto.getCompanyId(),
                                commonCompanyDto.getName(),
                                commonCompanyDto.getService())
                        )
                        .collect(Collectors.toList()));
    }
}
