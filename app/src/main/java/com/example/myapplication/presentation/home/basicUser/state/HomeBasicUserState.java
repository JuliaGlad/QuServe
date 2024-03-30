package com.example.myapplication.presentation.home.basicUser.state;

import com.example.myapplication.presentation.home.basicUser.model.HomeBasicUserModel;

public interface HomeBasicUserState {

    class Success implements HomeBasicUserState {
        public HomeBasicUserModel data;

        public Success(HomeBasicUserModel data){
            this.data = data;
        }
    }

    class Loading implements HomeBasicUserState {

    }

    class Error implements HomeBasicUserState {

    }
}
