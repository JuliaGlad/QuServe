package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;

public class UpdateApprovedUseCase {
    public void invoke(String companyId){
        DI.companyUserRepository.updateApproved(companyId);
    }
}
