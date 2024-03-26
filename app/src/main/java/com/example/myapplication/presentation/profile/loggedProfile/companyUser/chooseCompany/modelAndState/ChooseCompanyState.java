package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState;

import com.example.myapplication.presentation.profile.loggedProfile.companyUser.model.CompanyUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.state.CompanyUserState;

import java.util.List;

public interface ChooseCompanyState {

    class Success implements ChooseCompanyState {
        public ListItemModel data;

        public Success(ListItemModel data){
            this.data = data;
        }
    }

    class Loading implements ChooseCompanyState{}

    class Error implements ChooseCompanyState{}
}
