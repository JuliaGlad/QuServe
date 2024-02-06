package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.DI.service;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.QueueIdAndNameModel;
import com.example.myapplication.domain.model.QueueParticipantsListModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetParticipantsList {
    public Single<QueueParticipantsListModel> invoke() {
        return DI.queueRepository.getQueuesList().flatMap(queueDtos -> Single.just(Objects.requireNonNull(queueDtos
                .stream()
                .filter(queueDto -> queueDto.getAuthorId().equals(service.auth.getCurrentUser().getUid()))
                .map(queueDto -> new QueueParticipantsListModel(queueDto.getParticipants(), queueDto.getId()))
                .findFirst()
                .orElse(null))
        ));
    }
}
