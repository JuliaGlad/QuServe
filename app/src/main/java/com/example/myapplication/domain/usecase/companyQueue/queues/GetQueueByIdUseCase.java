package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.company.CompanyQueueNameModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetQueueByIdUseCase {

    public Single<CompanyQueueNameModel> invoke(String companyId, String queueId){
        return DI.companyQueueRepository.getCompaniesQueues(companyId).flatMap(companyQueueDtos ->
               Single.just(Objects.requireNonNull(companyQueueDtos
                       .stream()
                       .filter(companyQueueDto -> companyQueueDto.getId().equals(queueId))
                       .map(companyQueueDto -> new CompanyQueueNameModel(queueId, companyQueueDto.getName()))
                       .findFirst()
                       .orElse(null))));
    }
}
