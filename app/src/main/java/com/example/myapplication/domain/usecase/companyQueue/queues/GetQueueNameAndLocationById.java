package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.company.CompanyQueueNameAndLocationModel;

import io.reactivex.rxjava3.core.Single;

public class GetQueueNameAndLocationById {
    public Single<CompanyQueueNameAndLocationModel> invoke(String companyId, String queueId){
        return DI.companyQueueRepository.getSingleCompanyQueue(companyId, queueId).flatMap(companyQueueDto ->
                Single.just(new CompanyQueueNameAndLocationModel
                                (companyQueueDto.getName(), companyQueueDto.getLocation()))
        );
    }
}
