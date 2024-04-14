package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.di.DI.service;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.queue.QueueMidTimeModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetQueueMidTimeModel {
    public Single<QueueMidTimeModel> invoke() {
        return DI.queueRepository.getQueuesList().flatMap(queueDtos -> Single.just(Objects.requireNonNull(queueDtos
                .stream()
                .filter(queueDto -> queueDto.getAuthorId().equals(service.auth.getCurrentUser().getUid()))
                .map(queueDto -> new QueueMidTimeModel(queueDto.getMidTime(), queueDto.getId(), Integer.parseInt(queueDto.getPassed15Minutes())))
                .findFirst()
                .orElse(null))
        ));
    }
}
