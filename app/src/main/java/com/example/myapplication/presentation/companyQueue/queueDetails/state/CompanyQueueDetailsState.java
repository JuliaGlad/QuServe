package com.example.myapplication.presentation.companyQueue.queueDetails.state;

import com.example.myapplication.presentation.companyQueue.queueDetails.model.CompanyQueueDetailModel;

public interface CompanyQueueDetailsState {

    class Success implements CompanyQueueDetailsState{
        public CompanyQueueDetailModel data;

        public Success(CompanyQueueDetailModel data) {
            this.data = data;
        }
    }

    class Loading implements CompanyQueueDetailsState{}

    class Error implements CompanyQueueDetailsState{}
}
