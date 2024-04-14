package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;

import com.google.firebase.firestore.DocumentSnapshot;

public class OnParticipantLeftUseCase {
    public boolean invoke(DocumentSnapshot snapshot){
        return !snapshot.get(QUEUE_PARTICIPANTS_LIST).toString().contains(service.auth.getCurrentUser().getUid());
    }
}
