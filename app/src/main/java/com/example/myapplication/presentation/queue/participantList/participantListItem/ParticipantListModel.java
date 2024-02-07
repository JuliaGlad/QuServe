package com.example.myapplication.presentation.queue.participantList.participantListItem;

import java.util.List;

public class ParticipantListModel {
    int id;
    String queueID;
    List<String> list;
    String text;

    public ParticipantListModel(int id, String queueID, List<String> list, String text){
        this.id = id;
        this.queueID = queueID;
        this.list = list;
        this.text = text;
    }
}
