package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.state;

import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.model.SettingsUserModel;

public interface BasicSettingsState {

    class Success implements BasicSettingsState{
        public SettingsUserModel data;

        public Success(SettingsUserModel data) {
            this.data = data;
        }
    }

    class Loading implements BasicSettingsState{}

    class Error implements BasicSettingsState{}
}
