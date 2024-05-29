package com.example.myapplication.domain.usecase.profile.anonymous;

import static com.example.myapplication.presentation.utils.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.NOT_RESTAURANT_VISITOR;

import com.example.myapplication.data.dto.user.AnonymousUserDto;
import com.example.myapplication.data.providers.AnonymousUserProvider;

public class CheckAnonymousActionsUseCase {
    public boolean invoke(){
        AnonymousUserDto anonymous = AnonymousUserProvider.getUser();
        if (anonymous != null) {
            return anonymous.getParticipateInQueue().equals(NOT_PARTICIPATE_IN_QUEUE) && anonymous.getRestaurantVisitor().equals(NOT_RESTAURANT_VISITOR);
        } else {
            return true;
        }
    }

}
