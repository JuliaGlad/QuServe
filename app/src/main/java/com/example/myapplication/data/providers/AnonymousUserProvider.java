package com.example.myapplication.data.providers;

import com.example.myapplication.app.App;
import com.example.myapplication.data.db.dao.AnonymousUserDao;
import com.example.myapplication.data.db.entity.AnonymousUserEntity;
import com.example.myapplication.data.dto.user.AnonymousUserDto;

public class AnonymousUserProvider {

    public static AnonymousUserEntity getUser(){
        return App.getInstance().getDatabase().anonymousUserDao().getUser();
    }

    public static void insertUser(AnonymousUserDto dto){
        App.getInstance().getDatabase().anonymousUserDao()
                .insert(new AnonymousUserEntity(
                        dto.getUserId(),
                        dto.getParticipateInQueue(),
                        dto.getRestaurantVisitor()
                ));
    }

    public static void updateParticipateInQueue(String participateInQueue){
        AnonymousUserDao userDao = App.getInstance().getDatabase().anonymousUserDao();
        AnonymousUserEntity entity = userDao.getUser();
        entity.setParticipateInQueue(participateInQueue);
        userDao.update(entity);
    }

    public static void updateRestaurantVisitor(String restaurantVisitor){
        AnonymousUserDao userDao = App.getInstance().getDatabase().anonymousUserDao();
        AnonymousUserEntity entity = userDao.getUser();
        entity.setRestaurantVisitor(restaurantVisitor);
        userDao.update(entity);
    }

    public static void deleteUser(AnonymousUserEntity entity){
        AnonymousUserDao userDao = App.getInstance().getDatabase().anonymousUserDao();
        userDao.delete(entity);
    }

}
