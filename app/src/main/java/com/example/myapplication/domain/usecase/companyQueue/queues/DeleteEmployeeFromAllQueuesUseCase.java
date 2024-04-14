package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteEmployeeFromAllQueuesUseCase {
    public Completable invoke(String companyId, String employeeId){
        return DI.companyQueueRepository.deleteEmployeeFromAllQueues(companyId, employeeId);
    }
}
