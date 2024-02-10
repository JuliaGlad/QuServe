package com.example.myapplication.presentation.queue.participantList.participantListItem;

import java.util.List;


import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ParticipantListModel {
    int id;
    String text;

    public ParticipantListModel(int id, String text){
        this.id = id;
        this.text = text;
    }
}
