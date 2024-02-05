package com.example.myapplication.data.dto;

public class UserDto {
    private String userName;
    private String gender;
    private String phoneNumber;
    private String email;

    public UserDto(String userName, String gender, String phoneNumber, String email) {
        this.userName = userName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
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
