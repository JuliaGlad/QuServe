package com.example.myapplication.presentation.service.JoinQueueFragment.joinQueue.state;

import com.example.myapplication.presentation.service.JoinQueueFragment.joinQueue.model.JoinQueueModel;

public interface JoinQueueState {
    class Success implements JoinQueueState{
        public JoinQueueModel data;

        public Success(JoinQueueModel data) {
            this.data = data;
        }
    }

    class Loading implements JoinQueueState{}

    class Error implements JoinQueueState{}
}
