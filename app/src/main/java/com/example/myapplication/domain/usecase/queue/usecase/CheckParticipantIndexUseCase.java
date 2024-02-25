package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.DI.service;

import java.util.List;

public class CheckParticipantIndexUseCase {
    public boolean invoke(List<String> participantsList, int index){
        return participantsList.get(index).equals(service.auth.getCurrentUser().getUid());
    }
}
