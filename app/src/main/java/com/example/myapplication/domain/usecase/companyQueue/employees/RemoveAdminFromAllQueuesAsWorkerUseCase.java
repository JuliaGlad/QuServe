package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class RemoveAdminFromAllQueuesAsWorkerUseCase {
    public Completable invoke(String companyId, String adminId, String role){
        return DI.companyQueueRepository.removeAdminFromAllQueuesAsWorker(companyId, adminId, role);
    }
}
