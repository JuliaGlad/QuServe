package com.example.myapplication.presentation.employee.main.differentRolesFragment.state;

import com.example.myapplication.presentation.employee.main.differentRolesFragment.model.DifferentRoleModel;

import java.util.List;

public interface DifferentRoleState {
    class Success implements DifferentRoleState{
        public List<DifferentRoleModel> data;

        public Success(List<DifferentRoleModel> data) {
            this.data = data;
        }
    }
    class Loading implements DifferentRoleState{}

    class Error implements DifferentRoleState{}
}
