package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

import java.util.List;

public class RemoveUserFromParticipantsList {
    public void invoke(String queueID, String name){
        DI.queueRepository.removeUserFromParticipantList(queueID, name);
    }
}
