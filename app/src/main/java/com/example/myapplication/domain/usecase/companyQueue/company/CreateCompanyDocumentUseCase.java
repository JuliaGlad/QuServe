package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;

public class CreateCompanyDocumentUseCase {
    public void invoke( String companyID, String name, String email, String phone, String companyService){
        DI.companyUserRepository.createCompanyDocument(companyID, name, email, phone, companyService);
    }
}
