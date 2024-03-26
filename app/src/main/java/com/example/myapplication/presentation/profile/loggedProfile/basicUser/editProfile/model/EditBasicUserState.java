package com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.model;

public interface EditBasicUserState {

    class Success implements EditBasicUserState  {
        public EditUserModel data;
        public Success(EditUserModel data) {
            this.data = data;
        }
    }

    class Loading implements EditBasicUserState  {

    }

    class Error implements EditBasicUserState  {

    }
}