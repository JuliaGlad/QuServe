package com.example.myapplication.presentation.companyQueue.queueManager.state;

import com.example.myapplication.presentation.basicQueue.queueDetails.state.QueueDetailsState;
import com.example.myapplication.presentation.companyQueue.queueManager.model.QueueManagerModel;

import java.util.List;

public interface QueueManagerState {

    class Success implements QueueManagerState{
        public List<QueueManagerModel> data;

        public Success(List<QueueManagerModel> data) {
            this.data = data;
        }
    }

    class Loading implements QueueManagerState{}

    class Error implements QueueManagerState{}
}
