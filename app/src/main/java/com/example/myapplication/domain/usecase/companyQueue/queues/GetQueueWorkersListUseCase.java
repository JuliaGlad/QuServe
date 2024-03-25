package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;
import com.example.myapplication.data.dto.WorkerDto;
import com.example.myapplication.domain.model.company.WorkerModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
