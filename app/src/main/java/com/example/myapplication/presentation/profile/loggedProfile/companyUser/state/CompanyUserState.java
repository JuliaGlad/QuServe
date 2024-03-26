package com.example.myapplication.presentation.profile.loggedProfile.companyUser.state;

import com.example.myapplication.presentation.profile.loggedProfile.companyUser.model.CompanyUserModel;

public interface CompanyUserState {

    class Success implements CompanyUserState{
        public CompanyUserModel data;

        public Success(CompanyUserModel data){
            this.data = data;
        }
    }

    class Loading implements CompanyUserState{}

    class Error implements CompanyUserState{}
}
