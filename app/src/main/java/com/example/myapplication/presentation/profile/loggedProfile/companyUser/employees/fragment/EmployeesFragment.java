package com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.fragment;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentEmployeesBinding;
import com.example.myapplication.presentation.dialogFragments.changeRole.ChangeRoleDialogFragment;
import com.example.myapplication.presentation.dialogFragments.employeeQrCode.EmployeeQrCodeDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemAdapter;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.LinearLayoutManagerWrapper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class EmployeesFragment extends Fragment {

    private EmployeesViewModel viewModel;
    private FragmentEmployeesBinding binding;
    private String companyId;
    private List<EmployeeItemModel> basicList, workerList, adminList;
    private final EmployeeItemAdapter employeeItemAdapter = new EmployeeItemAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(EmployeesViewModel.class);
        binding = FragmentEmployeesBinding.inflate(inflater, container, false);
        companyId = getActivity().getIntent().getStringExtra(COMPANY_ID);
        viewModel.getEmployees(companyId, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initTabLayout();
        initSearchView();
        initAddButton();
    }

    private void initSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }

        });
    }

    private void filterList(String newText) {
        List<EmployeeItemModel> filteredList = new ArrayList<>();

        if (binding.tabLayout.getTabAt(0).isSelected()) {
            for (EmployeeItemModel model : basicList) {
                if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(model);
                }
            }
        } else if (binding.tabLayout.getTabAt(1).isSelected()) {
            for (EmployeeItemModel model : adminList) {
                if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(model);
                }
            }
        } else {
            for (EmployeeItemModel model : workerList) {
                if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(model);
                }
            }
        }

        setFilteredList(filteredList);
    }

    private void setFilteredList(List<EmployeeItemModel> models) {
        employeeItemAdapter.submitList(models);
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            EmployeeQrCodeDialogFragment dialogFragment = new EmployeeQrCodeDialogFragment(companyId);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "EMPLOYEE_QE_CODE_DIALOG");
        });
    }

    private void initTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        employeeItemAdapter.submitList(basicList);
                        break;
                    case 1:
                        employeeItemAdapter.submitList(adminList);
                        break;
                    case 2:
                        employeeItemAdapter.submitList(workerList);
                        break;
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initRecycler() {
        employeeItemAdapter.submitList(basicList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManagerWrapper(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(employeeItemAdapter);
    }

    private void setupObserves() {

        viewModel.showDialog.observe(getViewLifecycleOwner(), bundle -> {
            if (bundle != null) {

                ChangeRoleDialogFragment dialogFragment = new ChangeRoleDialogFragment(
                        companyId,
                        bundle.getString("ID"),
                        bundle.getString(EMPLOYEE_ROLE)
                );

                dialogFragment.show(getActivity().getSupportFragmentManager(), "CHANGE_ROLE_DIALOG");
                DialogDismissedListener listener = bundleDialog -> {
                    if (bundleDialog.getString(EMPLOYEE_ROLE).equals(ADMIN)) {
                        for (int i = 0; i < workerList.size(); i++) {
                            if (Objects.equals(workerList.get(i).getEmployeeId(), bundle.getString("ID"))) {
                                adminList.add(workerList.get(i));
                                workerList.remove(i);
                            }
                        }
                    } else {
                        for (int i = 0; i < adminList.size(); i++) {
                            if (adminList.get(i).getEmployeeId().equals(bundle.getString("ID"))) {
                                workerList.add(adminList.get(i));
                                adminList.remove(i);
                            }
                        }
                    }
                };
                dialogFragment.onDismissListener(listener);
            }
        });

        viewModel.onComplete.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                initRecycler();
            }
        });

        viewModel.addToAdmins.observe(getViewLifecycleOwner(), string -> {
            if (string != null) {
                for (int i = 0; i < workerList.size(); i++) {
                    if (Objects.equals(workerList.get(i).getEmployeeId(), string)) {
                        adminList.add(workerList.get(i));
                        workerList.remove(i);
                    }
                }
            }
        });

        viewModel.addToWorkers.observe(getViewLifecycleOwner(), string -> {
            if (string != null) {
                for (int i = 0; i < adminList.size(); i++) {
                    if (adminList.get(i).getEmployeeId().equals(string)) {
                        workerList.add(adminList.get(i));
                        adminList.remove(i);
                    }
                }
            }
        });

        viewModel.basicList.observe(getViewLifecycleOwner(), list -> {
            basicList = list;
        });

        viewModel.adminList.observe(getViewLifecycleOwner(), list -> {
            adminList = list;
        });

        viewModel.workerList.observe(getViewLifecycleOwner(), list -> {
            workerList = list;
        });
    }
}