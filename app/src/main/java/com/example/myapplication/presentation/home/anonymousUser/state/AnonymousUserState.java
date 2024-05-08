package com.example.myapplication.presentation.home.anonymousUser.state;

public interface AnonymousUserState {
    class Success implements AnonymousUserState{

    }

    class Loading implements AnonymousUserState{}

    class Error implements AnonymousUserState{}
}
