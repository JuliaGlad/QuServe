package com.example.myapplication.data.dto;

public class UserDto {
    private final String userName;
    private final String gender;
    private final String phoneNumber;
    private final String email;
    private final String birthday;
    private final String ownQueue;
    private final String participateInQueue;
    private final String restaurantVisitor;

    public UserDto(String userName, String gender, String phoneNumber, String email, String birthday, String ownQueue, String participateInQueue, String restaurantVisitor) {
        this.userName = userName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.ownQueue = ownQueue;
        this.participateInQueue = participateInQueue;
        this.restaurantVisitor = restaurantVisitor;
    }

    public String getBirthday(){return birthday;}

    public String isOwnQueue() {
        return ownQueue;
    }

    public String isRestaurantVisitor() {
        return restaurantVisitor;
    }

    public String isParticipateInQueue() {
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
