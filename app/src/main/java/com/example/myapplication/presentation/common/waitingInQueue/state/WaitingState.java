package com.example.myapplication.presentation.common.waitingInQueue.state;

import com.example.myapplication.presentation.common.waitingInQueue.model.WaitingModel;

public interface WaitingState {
    class Success implements WaitingState{
        public WaitingModel data;

        public Success(WaitingModel data){
            this.data = data;
        }
    }

    class Loading implements WaitingState{}

    class Error implements WaitingState{}
}