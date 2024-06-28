package com.example.myapplication.presentation.employee.main.navFragment;

import static com.example.myapplication.presentation.utils.constants.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.BASIC;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_WORKING;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEmployeeNavigationBinding;
import com.example.myapplication.presentation.employee.employeeUserModel.EmployeeRoleModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.chooseLocation.ChooseLocationFragment;
import com.example.myapplication.presentation.employee.main.differentRolesFragment.DifferentRolesEmployeeFragment;
import com.example.myapplication.presentation.employee.main.notEmployeeYetFragment.NotEmployeeYetFragment;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.QueueAdminFragment;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.QueueWorkerFragment;
import com.example.myapplication.presentation.employee.main.restaurantCook.CookEmployeeFragment;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.MainWaiterFragment;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.startWork.StartWorkFragment;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesCompany.fragment.EmployeesFragment;
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

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        switch (state) {
            case ANONYMOUS:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                        .setReorderingAllowed(true)
                        .replace(R.id.employee_nav_container, NotEmployeeYetFragment.class, null)
                        .commit();
                break;
            case BASIC:
                viewModel.isEmployee();
                setupObserves();
                break;
            case COMPANY:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                        .setReorderingAllowed(true)
                        .replace(R.id.employee_nav_container, EmployeesFragment.class, null)
                        .commit();
                break;
            case RESTAURANT:
                fragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                        .replace(R.id.employee_nav_container, ChooseLocationFragment.class, null)
                        .commit();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel.setArgumentsNull();
    }

    private void setupObserves() {
        viewModel.isEmployee.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null && !aBoolean) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                        .replace(R.id.employee_nav_container, NotEmployeeYetFragment.class, null)
                        .commit();
            } else if (aBoolean != null){
                viewModel.getCompanyRoles();
            }
        });


        viewModel.isWorking.observe(getViewLifecycleOwner(), bundle -> {
            if (bundle != null){
                boolean isWorking = bundle.getBoolean(IS_WORKING);
                if (isWorking) {
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                            .replace(R.id.employee_nav_container, MainWaiterFragment.class, bundle)
                            .commit();
                } else {
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                            .replace(R.id.employee_nav_container, StartWorkFragment.class, bundle)
                            .commit();
                }
            }
        });

        viewModel.getCompanyEmployeeRoles.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                viewModel.getRestaurantRoles();
            }
        });

        viewModel.roles.observe(getViewLifecycleOwner(), roles -> {
            if (roles != null) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                if (roles.size() > 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EMPLOYEE_ROLE, new Gson().toJson(roles));
                    fragmentManager
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                            .replace(R.id.employee_nav_container, DifferentRolesEmployeeFragment.class, bundle)
                            .commit();
                } else if (roles.size() == 1) {
                    EmployeeRoleModel employeeRoleModel = roles.get(0);

                    Bundle bundle = new Bundle();
                    bundle.putString(COMPANY_ID, employeeRoleModel.getCompanyId());

                    String role = employeeRoleModel.getRole();
                    switch (role) {
                        case WORKER:
                            fragmentManager
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                                    .replace(R.id.employee_nav_container, QueueWorkerFragment.class, bundle)
                                    .commit();
                            break;
                        case ADMIN:
                            bundle.putString(COMPANY_NAME, employeeRoleModel.getCompanyName());
                            fragmentManager
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                                    .replace(R.id.employee_nav_container, QueueAdminFragment.class, bundle)
                                    .commit();
                            break;
                        case COOK:
                            bundle.putString(LOCATION_ID, employeeRoleModel.getLocationId());
                            fragmentManager
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                                    .replace(R.id.employee_nav_container, CookEmployeeFragment.class, bundle)
                                    .commit();
                        case WAITER:
                            bundle.putString(LOCATION_ID, employeeRoleModel.getLocationId());
                            viewModel.checkIsWorking(employeeRoleModel.getCompanyId(), employeeRoleModel.getLocationId(), bundle);
                            break;
                    }
                }
            }
        });
    }
}