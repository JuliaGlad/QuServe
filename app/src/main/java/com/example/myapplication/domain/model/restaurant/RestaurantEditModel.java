package com.example.myapplication.domain.model.restaurant;

public class RestaurantEditModel {
   private final String name;
   private final String email;
   private final String phone;
   private final String service;

    public RestaurantEditModel(String name, String email, String phone, String service) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getService() {
        return service;
    }
}
