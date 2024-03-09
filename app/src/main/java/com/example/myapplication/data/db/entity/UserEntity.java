package com.example.myapplication.data.db.entity;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String userName;
    public String gender;
    public String phoneNumber;
    public String email;
    public String birthday;
    public String uri;
    public boolean ownQueue;
    public boolean participateInQueue;

    public UserEntity( String userName, String gender, String phoneNumber, String email, String birthday, String uri, boolean ownQueue, boolean participateInQueue) {
        this.userName = userName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.ownQueue = ownQueue;
        this.participateInQueue = participateInQueue;
        this.uri = uri;
    }
}
