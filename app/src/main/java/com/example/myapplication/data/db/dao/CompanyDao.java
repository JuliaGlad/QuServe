package com.example.myapplication.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.data.db.entity.CompanyUserEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface CompanyDao {

    @Query("SELECT * FROM companyUser")
    List<CompanyUserEntity> getCompanies();

    @Query("SELECT * FROM companyUser WHERE companyId =:companyId ")
    Maybe<CompanyUserEntity> getCompany(String companyId);

    @Insert
    void insertCompanyUser(CompanyUserEntity user);

    @Insert
    void insertAll(List<CompanyUserEntity> users);

    @Update
    void updateCompanyUser(CompanyUserEntity user);

    @Query("DELETE FROM companyUser")
    void deleteALl();

    @Delete
    void delete(CompanyUserEntity user);
}
