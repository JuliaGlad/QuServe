package com.example.myapplication.presentation.home.companyUser.state;

import com.example.myapplication.presentation.home.companyUser.models.QueueCompanyHomeModel;

import java.util.List;

public interface HomeQueueCompanyState {

    class Success implements HomeQueueCompanyState{
        public List<QueueCompanyHomeModel> data;

        public Success(List<QueueCompanyHomeModel> data) {
            this.data = data;
        }
    }

    class Loading implements HomeQueueCompanyState{}

    class Error implements HomeQueueCompanyState{}
}
