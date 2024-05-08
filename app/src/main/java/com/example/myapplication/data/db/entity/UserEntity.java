package com.example.myapplication.data.db.entity;

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
    public String ownQueue;
    public String participateInQueue;
    public String restaurantVisitor;

    public UserEntity(@NonNull String userName, String gender, String phoneNumber, String email, String birthday, String ownQueue, String participateInQueue, String restaurantVisitor) {
        this.userName = userName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.ownQueue = ownQueue;
        this.participateInQueue = participateInQueue;
        this.restaurantVisitor = restaurantVisitor;
    }
}
