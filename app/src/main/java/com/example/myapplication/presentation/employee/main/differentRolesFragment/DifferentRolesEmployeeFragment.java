package com.example.myapplication.presentation.employee.main.differentRolesFragment;

import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER;
import static com.example.myapplication.presentation.utils.constants.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDifferentRolesEmployeeBinding;
import com.example.myapplication.presentation.employee.employeeUserModel.EmployeeRoleModel;
import com.example.myapplication.presentation.employee.main.differentRolesFragment.delegate.CompanyEmployeeDelegate;
import com.example.myapplication.presentation.employee.main.differentRolesFragment.delegate.CompanyEmployeeDelegateItem;
import com.example.myapplication.presentation.employee.main.differentRolesFragment.delegate.CompanyEmployeeModel;
import com.example.myapplication.presentation.employee.main.differentRolesFragment.model.DifferentRoleModel;
import com.example.myapplication.presentation.employee.main.differentRolesFragment.state.DifferentRoleState;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.QueueAdminFragment;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.QueueWorkerFragment;
import com.example.myapplication.presentation.employee.main.restaurantCook.CookEmployeeFragment;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.MainWaiterFragment;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegate;
import myapplication.android.ui.recycler.ui.items.items.imageDrawable.ImageViewDrawableDelegate;
import myapplication.android.ui.recycler.ui.items.items.imageDrawable.ImageViewDrawableDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageDrawable.ImageViewDrawableModel;

public class DifferentRolesEmployeeFragment extends Fragment {

    private FragmentDifferentRolesEmployeeBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private List<EmployeeRoleModel> roleModels = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRoles();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDifferentRolesEmployeeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        initRecycler();
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new ImageViewDrawableDelegate());
        mainAdapter.addDelegate(new ButtonWithDescriptionDelegate());
        mainAdapter.addDelegate(new CompanyEmployeeDelegate());
        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void initRecycler() {
        final List<DelegateItem> delegates = new ArrayList<>();
        delegates.add(new ImageViewDrawableDelegateItem(new ImageViewDrawableModel(1, R.drawable.work_together_employee_wall_paper)));
        delegates.addAll(addDelegates(roleModels));
        mainAdapter.submitList(delegates);
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    private List<CompanyEmployeeDelegateItem> addDelegates(List<EmployeeRoleModel> roleModels) {
        List<CompanyEmployeeDelegateItem> delegates = new ArrayList<>();
        for (int i = 0; i < roleModels.size(); i++) {
            EmployeeRoleModel current = roleModels.get(i);
            String finalRole = current.getRole();
            String role = current.getRole();
            switch (role){
                case WAITER:
                    role = getString(R.string.waiter);
                    break;
                case COOK:
                    role = getString(R.string.cooker);
                    break;
                case WORKER:
                    role = getString(R.string.worker);
                    break;
                case ADMIN:
                    role = getString(R.string.admin);
                    break;
            }
            String companyId = current.getCompanyId();
            delegates.add(new CompanyEmployeeDelegateItem(new CompanyEmployeeModel(i + 2, companyId, current.getCompanyName(), role,
                    () -> {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                        Bundle bundle = new Bundle();
                        bundle.putString(COMPANY_ID, companyId);

                        switch (finalRole) {
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
                            case COOK:
                                bundle.putString(LOCATION_ID, current.getLocationId());
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.employee_nav_container, CookEmployeeFragment.class, bundle)
                                        .commit();
                            case WAITER:
                                bundle.putString(LOCATION_ID, current.getLocationId());
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.employee_nav_container, MainWaiterFragment.class, bundle)
                                        .commit();
                        }
                    }
            )));

        }
        return delegates;
    }

    private void getRoles() {
        assert getArguments() != null;
        String models = getArguments().getString(EMPLOYEE_ROLE);
        roleModels = new Gson().fromJson(models, new TypeToken<List<EmployeeRoleModel>>() {
        }.getType());
    }

}