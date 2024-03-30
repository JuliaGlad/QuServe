package com.example.myapplication.presentation.companyQueue.queueDetails.participantsList.state;

import java.util.List;

public interface CompanyParticipantsState {
    class Success implements CompanyParticipantsState{
        public List<String> data;

        public Success(List<String> data) {
            this.data = data;
        }
    }

    class Loading implements CompanyParticipantsState{}

    class Error implements CompanyParticipantsState{}
}
