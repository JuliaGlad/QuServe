package com.example.myapplication.presentation.service.waitingFragment.fragment.state;

import com.example.myapplication.presentation.service.waitingFragment.fragment.model.WaitingModel;

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