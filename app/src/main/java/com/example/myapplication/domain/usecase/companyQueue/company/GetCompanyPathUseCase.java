package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;

public class GetCompanyPathUseCase {
    public String invoke(String company){
        return DI.companyUserRepository.getCompanyPath(company);
    }
}
