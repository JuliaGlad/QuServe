package com.example.myapplication.domain.model.profile;

public class UserEditModel {

    private final String userName;
    private final String gender;
    private final String phoneNumber;
    private final String email;
    private final String birthday;

    public UserEditModel(String userName, String gender, String phoneNumber, String email, String birthday) {
        this.userName = userName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
    }

    public String getBirthday(){return birthday;}

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
