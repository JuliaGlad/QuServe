package com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel;

public interface BasicUserState {

    class Success implements BasicUserState {
        public BasicUserDataModel data;
        public Success(BasicUserDataModel data) {
            this.data = data;
        }
    }

    class Loading implements BasicUserState {

    }

    class Error implements BasicUserState {

    }
}