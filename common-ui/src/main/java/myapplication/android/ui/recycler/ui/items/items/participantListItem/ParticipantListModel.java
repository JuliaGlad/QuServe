package myapplication.android.ui.recycler.ui.items.items.participantListItem;

import java.util.List;


import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ParticipantListModel {
    int id;
    String text;
    int number;

    public ParticipantListModel(int id, String text, int number){
        this.id = id;
        this.text = text;
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
