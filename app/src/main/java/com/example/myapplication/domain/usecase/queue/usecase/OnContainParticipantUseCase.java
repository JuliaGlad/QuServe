package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_IN_PROGRESS;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class OnContainParticipantUseCase {
    public boolean invoke(DocumentSnapshot value){
        return Objects.equals(value.get(QUEUE_IN_PROGRESS), service.auth.getCurrentUser().getUid());
    }
}
