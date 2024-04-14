package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.queue.QueueModel;

import io.reactivex.rxjava3.core.Single;

public class GetQueueByParticipantPathUseCase {
    public Single<QueueModel> invoke(String path){
        return DI.queueRepository.getQueueByParticipantPath(path).map(dto ->
                new QueueModel(
                        dto.getName(),
                        dto.getParticipants(),
                        dto.getId(),
                        dto.getMidTime()
                ));
    }
}
