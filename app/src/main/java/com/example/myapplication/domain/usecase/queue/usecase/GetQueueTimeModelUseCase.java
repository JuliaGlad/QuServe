package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.DI.service;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.QueueTimeModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetQueueTimeModelUseCase {
    public Single<QueueTimeModel> invoke(){
        return DI.queueRepository.getQueuesList().flatMap(queueDtos ->
                Single.just(Objects.requireNonNull(queueDtos
                        .stream()
                        .filter(queueDto -> queueDto.getAuthorId().equals(service.auth.getCurrentUser().getUid()))
                        .map(queueDto -> new QueueTimeModel(queueDto.getId(), queueDto.getTime()))
                        .findFirst()
                        .orElse(null))
                ));
    }
}
