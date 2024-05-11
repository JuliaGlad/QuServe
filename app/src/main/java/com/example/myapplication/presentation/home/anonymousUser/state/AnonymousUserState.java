package com.example.myapplication.presentation.home.anonymousUser.state;

import com.example.myapplication.presentation.home.anonymousUser.models.AnonymousUserActionsHomeModel;

public interface AnonymousUserState {
    class Success implements AnonymousUserState{

    }

    class ActionsGot implements AnonymousUserState{
        public AnonymousUserActionsHomeModel data;

        public ActionsGot(AnonymousUserActionsHomeModel data) {
            this.data = data;
        }
    }

    class QueueDataGot implements AnonymousUserState{
        public String queueName;

        public QueueDataGot(String queueName) {
            this.queueName = queueName;
        }
    }

    class RestaurantDataGot implements AnonymousUserState{
        public String name;

        public RestaurantDataGot(String name) {
            this.name = name;
        }
    }

    class Loading implements AnonymousUserState{}

    class Error implements AnonymousUserState{}
}
