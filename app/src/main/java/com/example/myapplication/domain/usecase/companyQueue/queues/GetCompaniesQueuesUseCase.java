package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.company.CompanyQueueManagerModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetCompaniesQueuesUseCase {

    public Single<List<CompanyQueueManagerModel>> invoke(String companyId) {
        List<CompanyQueueManagerModel> list = new ArrayList<>();;
        return DI.companyQueueRepository.getCompaniesQueues(companyId)
                .map(companyDtos -> {
                    for (int i = 0; i < companyDtos.size(); i++) {
                        list.add(new CompanyQueueManagerModel(
                                companyDtos.get(i).getName(),
                                companyDtos.get(i).getId(),
                                companyDtos.get(i).getLocation(),
                                companyDtos.get(i).getCity(),
                                companyDtos.get(i).getWorkersCount())
                        );
                    }
                    return list;
                });
    }
}
