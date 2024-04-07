package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;
import com.example.myapplication.data.dto.CompanyQueueDto;
import com.example.myapplication.domain.model.queue.QueueTimeModel;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyQueueFinishTimeModelUseCase {
    public Single<String> invoke(String queueId, String companyId){
       return DI.companyQueueRepository.getSingleCompanyQueue(companyId, queueId).map(CompanyQueueDto::getTime);
    }
}
