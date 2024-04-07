package com.example.myapplication.presentation.employee.main.navFragment;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_PATH;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEmployeeNavigationBinding;
import com.example.myapplication.presentation.employee.employeeUserModel.EmployeeRoleModel;
import com.example.myapplication.presentation.employee.main.differentRolesFragment.DifferentRolesEmployeeFragment;
import com.example.myapplication.presentation.employee.main.notEmployeeYetFragment.NotEmployeeYetFragment;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.QueueAdminFragment;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.QueueWorkerFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.fragment.EmployeesFragment;
import com.google.gson.Gson;

public class EmployeeNavigationFragment extends Fragment {

    private EmployeeNavigationViewModel viewModel;
    private FragmentEmployeeNavigationBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EmployeeNavigationViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEmployeeNavigationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String state = sharedPreferences.getString(APP_STATE, ANONYMOUS);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (state){
            case ANONYMOUS:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.employee_nav_container, NotEmployeeYetFragment.class, null)
                        .commit();
                break;
            case BASIC:
                viewModel.isEmployee();
                setupObserves();
                break;
            case COMPANY:
                fragmentManager.beginTransaction()
                        .replace(R.id.employee_nav_container, EmployeesFragment.class, null)
                        .commit();
                break;
        }
    }

    private void setupObserves() {
        viewModel.isEmployee.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null && !aBoolean){
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.employee_nav_container, NotEmployeeYetFragment.class, null)
                        .commit();
            } else {
                viewModel.getRoles();
            }
        });

        viewModel.roles.observe(getViewLifecycleOwner(), roles -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (roles.size() > 1){
                Bundle bundle = new Bundle();
                bundle.putString(EMPLOYEE_ROLE, new Gson().toJson(roles));
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.employee_nav_container, DifferentRolesEmployeeFragment.class, bundle)
                        .commit();
            } else if (roles.size() == 1){
                EmployeeRoleModel employeeRoleModel = roles.get(0);

                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, employeeRoleModel.getCompanyId());

                switch (employeeRoleModel.getRole()){
                    case WORKER:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.employee_nav_container, QueueWorkerFragment.class, bundle)
                                .commit();
                        break;
                    case ADMIN:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.employee_nav_container, QueueAdminFragment.class, bundle)
                                .commit();
                        break;
                }
            }
        });
    }
}