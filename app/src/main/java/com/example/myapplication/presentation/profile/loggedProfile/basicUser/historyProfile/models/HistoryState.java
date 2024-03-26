package com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.models;


import java.util.List;

public interface HistoryState {

    class Success implements HistoryState{
        public List<HistoryItemModel> data;

        public Success(List<HistoryItemModel> data) {
            this.data = data;
        }
    }

    class Loading implements HistoryState{ }

    class Error implements HistoryState{}
}
