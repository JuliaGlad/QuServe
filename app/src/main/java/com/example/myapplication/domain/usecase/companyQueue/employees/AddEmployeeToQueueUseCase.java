package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddEmployeeToQueueUseCase {
    public Completable invoke(String companyId, String queueId){
        return DI.companyQueueRepository.addEmployeeToQueue(companyId, queueId);
    }
}
