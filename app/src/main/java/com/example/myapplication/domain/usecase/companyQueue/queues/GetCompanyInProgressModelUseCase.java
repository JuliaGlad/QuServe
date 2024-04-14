package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.data.dto.CompanyQueueDto;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyInProgressModelUseCase {
    public Single<String> invoke(String companyId, String queueId){
        return DI.companyQueueRepository.getSingleCompanyQueue(companyId, queueId).map(CompanyQueueDto::getInProgress);
    }
}
