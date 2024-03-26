package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.privacySettings.state;

public interface PrivacySettingState {

    class Success implements PrivacySettingState{}

    class Loading implements PrivacySettingState{}

    class Error implements PrivacySettingState{}
}
