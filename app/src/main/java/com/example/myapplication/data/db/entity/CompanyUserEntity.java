package com.example.myapplication.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "companyUser")
public class CompanyUserEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String companyId;
    public String ownerId;
    public String name;
    public String phoneNumber;
    public String email;
    public String service;
    public String logo;

    public CompanyUserEntity(String companyId, String ownerId, String name, String phoneNumber, String email, String service, String logo) {
        this.companyId = companyId;
        this.ownerId = ownerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.service = service;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getCompanyId() {
        return companyId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getService() {
        return service;
    }

    public String getLogo() {
        return logo;
    }
}
