package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.QueueIdAndNameModel;
import com.example.myapplication.domain.model.QueueNameModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetQueueByQueueIdUseCase {
    public Single<QueueNameModel> invoke(String queueId){
        return DI.queueRepository.getQueuesList().flatMap(queueDtos ->
                Single.just(Objects.requireNonNull(queueDtos
                        .stream()
                        .filter(queueDto -> queueDto.getId().equals(queueId))
                        .map(queueDto -> new QueueNameModel( queueDto.getName()))
                        .findFirst()
                        .orElse(null))
        ));
    }
}
