package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.data.dto.company.CompanyQueueDto;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyQueueFinishTimeModelUseCase {
    public Single<String> invoke(String queueId, String companyId){
       return DI.companyQueueRepository.getSingleCompanyQueue(companyId, queueId).map(CompanyQueueDto::getTime);
    }
}
