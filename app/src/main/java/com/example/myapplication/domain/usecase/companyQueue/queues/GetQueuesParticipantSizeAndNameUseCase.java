package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.data.dto.CompanyQueueDto;
import com.example.myapplication.domain.model.company.CompanyQueueParticipantsSizeAndNameModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetQueuesParticipantSizeAndNameUseCase {
    public Single<List<CompanyQueueParticipantsSizeAndNameModel>> invoke(String companyId){
        List<CompanyQueueParticipantsSizeAndNameModel> list = new ArrayList<>();;
        return DI.companyQueueRepository.getCompaniesQueues(companyId)
                .map(companyDtos -> {
                    for (int i = 0; i < companyDtos.size(); i++) {
                        CompanyQueueDto current = companyDtos.get(i);
                        list.add(new CompanyQueueParticipantsSizeAndNameModel(
                                current.getId(),
                                current.getParticipants().size(),
                                current.getName()
                        ));
                    }
                    return list;
                });
    }

}
