package com.example.myapplication.data.providers;

import com.example.myapplication.app.App;
import com.example.myapplication.data.db.entity.CompanyUserEntity;
import com.example.myapplication.data.dto.CompanyDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanyUserProvider {

    public static List<CompanyDto> getCompanies() {
        List<CompanyDto> dtos = new ArrayList<>();
        List<CompanyUserEntity> list = App.getInstance().getDatabase().companyDao().getCompanies();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                dtos.add(new CompanyDto(
                        list.get(i).companyId,
                        list.get(i).logo,
                        list.get(i).name,
                        list.get(i).email,
                        list.get(i).phoneNumber,
                        list.get(i).service
                ));
            }
            return dtos;
        } else {
            return null;
        }
    }

    public static void insertCompany(CompanyDto companyDto) {
        App.getInstance().getDatabase().companyDao().insertCompanyUser(new CompanyUserEntity(
                companyDto.getId(),
                companyDto.getCompanyName(),
                companyDto.getCompanyPhone(),
                companyDto.getCompanyEmail(),
                companyDto.getCompanyService(),
                companyDto.getUri()
        ));
    }

    public static void insertAllCompanies(List<CompanyDto> list) {
        List<CompanyUserEntity> entities = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entities.add(new CompanyUserEntity(
                    list.get(i).getId(),
                    list.get(i).getCompanyName(),
                    list.get(i).getCompanyPhone(),
                    list.get(i).getCompanyEmail(),
                    list.get(i).getCompanyService(),
                    list.get(i).getUri())
            );
        }
        App.getInstance().getDatabase().companyDao().insertAll(entities);
    }

    public static void updateCompany(String companyId, String name, String phoneNumber) {
        List<CompanyUserEntity> entities = App.getInstance().getDatabase().companyDao().getCompanies();
        CompanyUserEntity entity = null;
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getCompanyId().equals(companyId)) {
                entity = entities.get(i);
                break;
            }
        }
        if (entity != null) {
            if (!Objects.equals(entity.phoneNumber, phoneNumber) && phoneNumber != null) {
                entity.phoneNumber = phoneNumber;
            }
            if (!entity.name.equals(name) && name != null){
                entity.name = name;
            }

            App.getInstance().getDatabase().companyDao().updateCompanyUser(entity);
        }
    }

    public static void updateUri(String companyId, String uri){
        List<CompanyUserEntity> entities = App.getInstance().getDatabase().companyDao().getCompanies();
        CompanyUserEntity entity = null;
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getCompanyId().equals(companyId)) {
                entity = entities.get(i);
                break;
            }
        }

        if (entity != null){
            if (uri != null && !entity.logo.equals(uri)){
                entity.logo = uri;
            }
            App.getInstance().getDatabase().companyDao().updateCompanyUser(entity);
        }
    }

    public static void deleteCompany(String companyId) {
        List<CompanyUserEntity> entities = App.getInstance().getDatabase().companyDao().getCompanies();
        CompanyUserEntity entity = null;
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getCompanyId().equals(companyId)) {
                entity = entities.get(i);
                break;
            }
        }

        if (entity != null){
            App.getInstance().getDatabase().companyDao().delete(entity);
        }
    }

    public static void deleteAll(){
        App.getInstance().getDatabase().companyDao().deleteALl();
    }
}
