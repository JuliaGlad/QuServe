package com.example.myapplication.presentation.employee.main.differentRolesFragment;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.WORKER;
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

    private DifferentRolesEmployeeViewModel viewModel;
    private FragmentDifferentRolesEmployeeBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private List<EmployeeRoleModel> roleModels = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DifferentRolesEmployeeViewModel.class);
        getRoles();
        viewModel.getEmployeeCompanyRoles(roleModels);
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
        setupObserves();
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new ImageViewDrawableDelegate());
        mainAdapter.addDelegate(new ButtonWithDescriptionDelegate());
        mainAdapter.addDelegate(new CompanyEmployeeDelegate());
        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void initRecycler(List<DifferentRoleModel> roleModels) {
        final List<DelegateItem> delegates = new ArrayList<>();
        delegates.add(new ImageViewDrawableDelegateItem(new ImageViewDrawableModel(1, R.drawable.work_together_employee_wall_paper)));
        delegates.addAll(addDelegates(roleModels));
        mainAdapter.submitList(delegates);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    private List<CompanyEmployeeDelegateItem> addDelegates(List<DifferentRoleModel> roleModels) {
        List<CompanyEmployeeDelegateItem> delegates = new ArrayList<>();
        for (int i = 0; i < roleModels.size(); i++) {
            DifferentRoleModel current = roleModels.get(i);
            String role = current.getRole();
            String companyId = current.getCompanyId();
            delegates.add(new CompanyEmployeeDelegateItem(new CompanyEmployeeModel(i + 2, companyId, current.getCompanyName(), role,
                    () -> {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        Bundle bundle = new Bundle();
                        bundle.putString(COMPANY_ID, companyId);

                        switch (role){
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
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.employee_nav_container, CookEmployeeFragment.class, bundle)
                                        .commit();
                        }
                    }
            )));

        }
        return delegates;
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof DifferentRoleState.Success) {
                List<DifferentRoleModel> models = ((DifferentRoleState.Success) state).data;
                initRecycler(models);
            } else if (state instanceof DifferentRoleState.Loading) {

            } else if (state instanceof DifferentRoleState.Error) {
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getEmployeeCompanyRoles(roleModels);
        });
    }

    private void getRoles() {
        assert getArguments() != null;
        String models = getArguments().getString(EMPLOYEE_ROLE);
        roleModels = new Gson().fromJson(models, new TypeToken<List<EmployeeRoleModel>>() {
        }.getType());
        assert roleModels != null;
    }

}