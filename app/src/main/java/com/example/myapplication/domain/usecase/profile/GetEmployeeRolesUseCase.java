package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.data.dto.UserEmployeeRoleDto;
import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.domain.model.profile.UserEmployeeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetEmployeeRolesUseCase {
    public Single<List<UserEmployeeModel>> invoke() {
        return ProfileEmployeeDI.companyEmployee.getEmployeeRoles().map(userEmployeeRoleDtos ->
                userEmployeeRoleDtos.stream()
                        .map(userEmployeeRoleDto -> new UserEmployeeModel(
                                userEmployeeRoleDto.getRole(),
                                userEmployeeRoleDto.getCompanyId(),
                                null)
                        ).collect(Collectors.toList()));
    }
}
