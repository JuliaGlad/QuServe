package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_IN_PROGRESS;

import com.google.firebase.firestore.DocumentSnapshot;

public class OnContainParticipantUseCase {
    public boolean invoke(DocumentSnapshot value){
        return value.get(QUEUE_IN_PROGRESS).equals(service.auth.getCurrentUser().getUid());
    }
}
