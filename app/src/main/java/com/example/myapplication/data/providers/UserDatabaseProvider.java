package com.example.myapplication.data.providers;

import static com.example.myapplication.di.DI.service;

import android.util.Log;

import com.example.myapplication.App;
import com.example.myapplication.data.db.entity.UserEntity;
import com.example.myapplication.data.dto.user.UserDto;

import java.util.List;

public class UserDatabaseProvider {

    public static void deleteUser() {
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (!entities.isEmpty()) {
            UserEntity entity = entities.get(0);
            App.getInstance().getDatabase().userDao().delete(entity);
        }
    }

    public static void insertUser(UserDto userDto) {
        App.getInstance().getDatabase().userDao().insert(
                new UserEntity(
                        userDto.getUserName(),
                        userDto.getGender(),
                        userDto.getPhoneNumber(),
                        userDto.getEmail(),
                        userDto.getBirthday(),
                        userDto.isOwnQueue(),
                        userDto.isParticipateInQueue(),
                        userDto.isRestaurantVisitor()
                )
        );
    }

    public static UserDto getUser() {
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (!entities.isEmpty()) {
            UserEntity entity = entities.get(0);
            return new UserDto(
                    entity.userName,
                    entity.gender,
                    entity.phoneNumber,
                    entity.email,
                    entity.birthday,
                    entity.ownQueue,
                    entity.participateInQueue,
                    entity.restaurantVisitor
            );
        } else {
            return null;
        }
    }

    public static void updateUser(String userName, String gender, String phoneNumber, String birthday) {
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (!entities.isEmpty()) {
            UserEntity entity = entities.get(0);

            if (!entity.userName.equals(userName) && userName != null) {
                entity.userName = userName;
            }
            if (!entity.gender.equals(gender) && gender != null) {
                entity.gender = gender;
            }
            if (!entity.phoneNumber.equals(phoneNumber)) {
                entity.phoneNumber = phoneNumber;
            }
            if (birthday != null && !entity.birthday.equals(birthday) ) {
                entity.birthday = birthday;
            }

            App.getInstance().getDatabase().userDao().update(entity);
        }
    }

    public static void updateOwnQueue(String isOwn){
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (!entities.isEmpty()) {
            UserEntity entity = entities.get(0);
            entity.ownQueue = isOwn;

            App.getInstance().getDatabase().userDao().update(entity);
        }
    }

    public static void updateParticipateInQueue(String path){
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (!entities.isEmpty()) {
            UserEntity entity = entities.get(0);
            entity.setParticipateInQueue(path);
            App.getInstance().getDatabase().userDao().update(entity);
        }
    }

    public static void updateRestaurantVisitor(String path){
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (!entities.isEmpty()) {
            UserEntity entity = entities.get(0);
            entity.restaurantVisitor = path;
            Log.d("Path", path);
            App.getInstance().getDatabase().userDao().update(entity);
        }
    }
}
