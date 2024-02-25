package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

public class UpdateMidTimeUseCase {
    public void invoke(String queueId, int previous, int passed){
        DI.queueRepository.updateTimePassed(queueId, previous, passed);
    }
}
