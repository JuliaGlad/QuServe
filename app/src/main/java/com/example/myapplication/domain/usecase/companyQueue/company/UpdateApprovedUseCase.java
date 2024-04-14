package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.di.DI;

public class UpdateApprovedUseCase {
    public void invoke(String companyId){
        DI.companyQueueUserRepository.updateApproved(companyId);
    }
}
