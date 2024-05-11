package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesCompany.fragment;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import android.content.Context;
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
import com.example.myapplication.presentation.dialogFragments.deleteEmployeeFromCompany.DeleteEmployeeFromCompanyDialogFragment;
import com.example.myapplication.presentation.dialogFragments.employeeQrCode.EmployeeQrCodeDialogFragment;
import com.example.myapplication.presentation.employee.main.companiesEmployees.model.EmployeeModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesCompany.fragment.recyclerViewItem.EmployeeItemAdapter;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesCompany.fragment.recyclerViewItem.EmployeeItemModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesCompany.fragment.recyclerViewItem.LinearLayoutManagerWrapper;
import com.example.myapplication.presentation.employee.main.companiesEmployees.state.EmployeeState;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class EmployeesFragment extends Fragment {

    private EmployeesViewModel viewModel;
    private FragmentEmployeesBinding binding;
    private String companyId;
    private final List<EmployeeItemModel> basicList = new ArrayList<>();
    private final List<EmployeeItemModel> workerList = new ArrayList<>();
    private final List<EmployeeItemModel> adminList = new ArrayList<>();
    private final EmployeeItemAdapter employeeItemAdapter = new EmployeeItemAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(EmployeesViewModel.class);
        binding = FragmentEmployeesBinding.inflate(inflater, container, false);
        companyId = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(COMPANY_ID, null);
        viewModel.getEmployees(companyId);
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
        binding.progressBar.setVisibility(View.GONE);
    }

    private void removeEmployee(String userId, String role) {
        for (int i = 0; i < basicList.size(); i++) {
            if (basicList.get(i).getEmployeeId().equals(userId)) {
                basicList.remove(i);
                break;
            }
        }

        switch (role) {
            case ADMIN:
                for (int i = 0; i < adminList.size(); i++) {
                    if (adminList.get(i).getEmployeeId().equals(userId)) {
                        adminList.remove(i);
                        break;
                    }
                }
                break;
            case WORKER:
                for (int i = 0; i < workerList.size(); i++) {
                    if (workerList.get(i).getEmployeeId().equals(userId)) {
                        workerList.remove(i);
                        break;
                    }
                }
        }
    }

    private void addToAdmins(String id) {
        for (int i = 0; i < workerList.size(); i++) {
            if (Objects.equals(workerList.get(i).getEmployeeId(), id)) {
                adminList.add(workerList.get(i));
                workerList.remove(i);
                break;
            }
        }
    }

    private void addToWorkers(String id) {
        for (int i = 0; i < adminList.size(); i++) {
            if (adminList.get(i).getEmployeeId().equals(id)) {
                workerList.add(adminList.get(i));
                adminList.remove(i);
                break;
            }
        }
    }

    private void initSubLists() {
        for (int i = 0; i < basicList.size(); i++) {
            if (basicList.get(i).getRole().equals(WORKER)) {
                workerList.add(basicList.get(i));
            } else {
                adminList.add(basicList.get(i));
            }
        }
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof EmployeeState.Success) {
                List<EmployeeModel> models = ((EmployeeState.Success) state).data;
                for (int i = 0; i < models.size(); i++) {
                    EmployeeModel current = models.get(i);

                    String id = current.getId();
                    String name = current.getName();
                    String role = current.getRole();

                    final ChangeRoleDialogFragment[] dialogFragment = {new ChangeRoleDialogFragment(companyId, id, role)};

                    basicList.add(new EmployeeItemModel(i, this, name, id, role, companyId, dialogFragment[0], () -> {

                        dialogFragment[0].show(getActivity().getSupportFragmentManager(), "CHANGE_ROLE_DIALOG");

                        DialogDismissedListener listener = bundleDialog -> {
                            if (bundleDialog.getString(EMPLOYEE_ROLE).equals(ADMIN)) {
                                addToAdmins(id);
                                dialogFragment[0] = new ChangeRoleDialogFragment(companyId, id, ADMIN);
                            } else {
                                addToWorkers(id);
                                dialogFragment[0] = new ChangeRoleDialogFragment(companyId, id, WORKER);
                            }
                        };

                        dialogFragment[0].onDismissListener(listener);
                    }, () -> {

                        showDeleteDialog(id, companyId);

                    }));


                }
                initSubLists();
                initRecycler();
            } else if (state instanceof EmployeeState.Loading) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else if (state instanceof EmployeeState.Error) {

            }
        });
    }

    private void showDeleteDialog(String employeeId, String companyId) {
        DeleteEmployeeFromCompanyDialogFragment dialogFragment = new DeleteEmployeeFromCompanyDialogFragment(employeeId, companyId);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "DELETE_EMPLOYEE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            removeEmployee(employeeId, bundle.getString(EMPLOYEE_ROLE));
        });
    }

}