package com.example.myapplication.data.dto;

public class UserDto {
    private final String userName;
    private final String gender;
    private final String phoneNumber;
    private final String email;
    private final boolean ownQueue;
    private final boolean participateInQueue;

    public UserDto(String userName, String gender, String phoneNumber, String email, boolean ownQueue, boolean participateInQueue) {
        this.userName = userName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ownQueue = ownQueue;
        this.participateInQueue = participateInQueue;
    }

    public boolean isOwnQueue() {
        return ownQueue;
    }

    public boolean isParticipateInQueue() {
        return participateInQueue;
    }

    public String getUserName() {
        return userName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }


}
