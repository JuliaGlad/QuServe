package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.company.WorkerModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetQueueWorkersListUseCase {

    public Single<List<WorkerModel>> invoke(String companyId, String queueId){
        List<WorkerModel> workers = new ArrayList<>();
        return DI.companyQueueRepository.getWorkersList(companyId, queueId).map(workerDtos -> {
            for (int i = 0; i < workerDtos.size(); i++) {
                workers.add(new WorkerModel(workerDtos.get(i).getName(), workerDtos.get(i).getId()));
            }
            return workers;
        });

    }
}
