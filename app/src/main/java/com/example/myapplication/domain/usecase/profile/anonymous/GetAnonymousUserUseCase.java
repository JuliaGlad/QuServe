package com.example.myapplication.domain.usecase.profile.anonymous;

import com.example.myapplication.data.db.entity.AnonymousUserEntity;
import com.example.myapplication.data.providers.AnonymousUserProvider;
import com.example.myapplication.domain.model.profile.AnonymousUserModel;

public class GetAnonymousUserUseCase {
    public AnonymousUserModel invoke(){
        AnonymousUserEntity entity = AnonymousUserProvider.getUser();
        return new AnonymousUserModel(entity.getRestaurantVisitor(), entity.getParticipateInQueue());
    }
}
