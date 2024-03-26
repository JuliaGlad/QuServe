package com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany.state;

import com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany.model.EditCompanyModel;

public interface EditCompanyState {

    class Success implements EditCompanyState{
        public EditCompanyModel data;

        public Success(EditCompanyModel data) {
            this.data = data;
        }
    }

    class Loading implements EditCompanyState{}

    class Error implements EditCompanyState{}
}
