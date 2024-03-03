package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.DI.service;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.queue.QueueModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetQueueByParticipantIdUseCase {
    public Single<QueueModel> invoke() {
        return DI.queueRepository.getQueuesList().flatMap(queueDtos -> Single.just(Objects.requireNonNull(queueDtos
                .stream()
                .filter(queueDto -> queueDto.getAuthorId().equals(service.auth.getCurrentUser().getUid()))
                .map(queueDto -> new QueueModel(queueDto.getName(), queueDto.getParticipants(), queueDto.getId(), queueDto.getMidTime()))
                .findFirst()
                .orElse(null))));
    }
}
