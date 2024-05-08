package com.example.myapplication.di.company;

import com.example.myapplication.domain.usecase.commonCompany.CheckAnyCompanyExistUseCase;
import com.example.myapplication.domain.usecase.commonCompany.GetAllCompaniesLogosUseCase;
import com.example.myapplication.domain.usecase.commonCompany.GetAllServiceCompaniesUseCase;
import com.example.myapplication.domain.usecase.commonCompany.SetCompanyUserUseCase;
import com.example.myapplication.domain.usecase.companyQueue.company.GetCompaniesLogosUseCase;

public class CommonCompanyDI {
    public static CheckAnyCompanyExistUseCase checkAnyCompanyExistUseCase = new CheckAnyCompanyExistUseCase();
    public static GetAllServiceCompaniesUseCase getAllServiceCompaniesUseCase = new GetAllServiceCompaniesUseCase();
    public static GetAllCompaniesLogosUseCase getAllCompaniesLogosUseCase = new GetAllCompaniesLogosUseCase();
    public static SetCompanyUserUseCase setCompanyUserUseCase = new SetCompanyUserUseCase();
}
