package com.example.myapplication.presentation.basicQueue.queueDetails.participantList.state;

import java.util.List;

public interface ParticipantsListState {

    class Success implements ParticipantsListState {
        public final List<String> data;

        public Success(List<String> data) {
            this.data = data;
        }
    }

    class Loading implements ParticipantsListState {
    }

    class Error implements ParticipantsListState {
    }
}
