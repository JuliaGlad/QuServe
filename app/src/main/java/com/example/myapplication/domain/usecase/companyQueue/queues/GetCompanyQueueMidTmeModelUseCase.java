package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyQueueMidTimeModel;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyQueueMidTmeModelUseCase{

    public Single<CompanyQueueMidTimeModel> invoke(String queueId, String companyId){
        return DI.companyQueueRepository.getSingleCompanyQueue(companyId, queueId).map(dto ->
                new CompanyQueueMidTimeModel(dto.getMidTime(), dto.getPassed()));
    }
}
