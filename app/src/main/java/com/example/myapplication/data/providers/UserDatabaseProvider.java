package com.example.myapplication.data.providers;

import android.util.Log;

import com.example.myapplication.app.App;
import com.example.myapplication.data.db.entity.UserEntity;
import com.example.myapplication.data.dto.UserDto;

import java.util.List;

public class UserDatabaseProvider {

    public static void deleteUser() {
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (entities.size() > 0) {
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
                        userDto.getUri(),
                        userDto.getBackground(),
                        userDto.isOwnQueue(),
                        userDto.isParticipateInQueue()
                )
        );
    }

    public static UserDto getUser() {
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        Log.d("Entities size", String.valueOf(entities.size()));
        if (entities.size() > 0) {
            UserEntity entity = entities.get(0);
            return new UserDto(
                    entity.userName,
                    entity.gender,
                    entity.phoneNumber,
                    entity.email,
                    entity.birthday,
                    entity.uri,
                    entity.background,
                    entity.ownQueue,
                    entity.participateInQueue
            );
        } else {
            return null;
        }
    }

    public static void updateUser(String userName, String gender, String phoneNumber, String birthday) {
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (entities.size() > 0) {
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

    public static void updateUri(String uri) {
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (entities.size() > 0) {
            UserEntity entity = entities.get(0);
            entity.uri = uri;
            App.getInstance().getDatabase().userDao().update(entity);
        }
    }

    public static void updateBackground(String uri) {
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (entities.size() > 0) {
            UserEntity entity = entities.get(0);
            entity.background = uri;
            App.getInstance().getDatabase().userDao().update(entity);
        }
    }

    public static void updateOwnQueue(boolean isOwn){
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (entities.size() > 0) {
            UserEntity entity = entities.get(0);

            entity.ownQueue = isOwn;

            App.getInstance().getDatabase().userDao().update(entity);
        }
    }

    public static void updateParticipateInQueue(boolean isParticipant){
        List<UserEntity> entities = App.getInstance().getDatabase().userDao().getUser();
        if (entities.size() > 0) {
            UserEntity entity = entities.get(0);

            entity.participateInQueue = isParticipant;

            App.getInstance().getDatabase().userDao().update(entity);
        }
    }
}
