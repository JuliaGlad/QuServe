package com.example.myapplication.domain.usecase.profile.employee.company;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteActiveQueueUseCase {
    public Completable invoke(String companyId, String queueId, String userId){
        return ProfileEmployeeDI.companyEmployee.deleteActiveQueue(companyId, queueId, userId);
    }
}
