package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteEmployeeFromQueueUseCase {
    public Completable invoke(String companyId, String queueId, String employeeId){
        return DI.companyQueueRepository.deleteEmployeeFromQueue(companyId, queueId, employeeId);
    }
}
