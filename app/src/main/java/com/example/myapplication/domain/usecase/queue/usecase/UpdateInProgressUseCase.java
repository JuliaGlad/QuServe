package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

public class UpdateInProgressUseCase {
    public void invoke(String queueId, String name, int passed){
        DI.queueRepository.updateInProgress(queueId, name, passed);
    }
}
