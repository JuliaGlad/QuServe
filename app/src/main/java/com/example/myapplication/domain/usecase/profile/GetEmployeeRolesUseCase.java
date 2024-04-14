package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.data.dto.UserEmployeeRoleDto;
import com.example.myapplication.domain.model.profile.UserEmployeeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetEmployeeRolesUseCase {
    public Single<List<UserEmployeeModel>> invoke() {
        List<UserEmployeeModel> models = new ArrayList<>();
        return DI.profileRepository.getEmployeeRoles().map(userEmployeeRoleDtos -> {
            for (int i = 0; i < userEmployeeRoleDtos.size(); i++) {
                UserEmployeeRoleDto current = userEmployeeRoleDtos.get(i);

                models.add(new UserEmployeeModel(current.getRole(), current.getCompanyId()));
            }
            return models;
        });
    }
}
