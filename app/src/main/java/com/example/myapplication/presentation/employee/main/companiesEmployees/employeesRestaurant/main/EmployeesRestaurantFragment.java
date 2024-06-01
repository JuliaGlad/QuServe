package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.main;

import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentEmployeesRestaurantBinding;
import com.example.myapplication.presentation.dialogFragments.deleteRestaurantEmployee.DeleteRestaurantEmployeeDialogFragment;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.recycler.RestaurantEmployeeItemAdapter;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.recycler.RestaurantEmployeeItemModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.model.EmployeeModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.state.EmployeeState;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class EmployeesRestaurantFragment extends Fragment {

    private EmployeesRestaurantViewModel viewModel;
    private FragmentEmployeesRestaurantBinding binding;
    private final RestaurantEmployeeItemAdapter adapter = new RestaurantEmployeeItemAdapter();
    private final List<RestaurantEmployeeItemModel> basicList = new ArrayList<>();
    private final List<RestaurantEmployeeItemModel> waiterList = new ArrayList<>();
    private final List<RestaurantEmployeeItemModel> cooksList = new ArrayList<>();
    private String restaurantId, locationId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EmployeesRestaurantViewModel.class);
        restaurantId = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(COMPANY_ID, null);
        locationId = getArguments().getString(LOCATION_ID);
        viewModel.getEmployees(restaurantId, locationId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEmployeesRestaurantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initTabLayout();
        initSearchView();
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
        List<RestaurantEmployeeItemModel> filteredList = new ArrayList<>();

        if (binding.tabLayout.getTabAt(0).isSelected()) {
            for (RestaurantEmployeeItemModel model : basicList) {
                if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(model);
                }
            }
        } else if (binding.tabLayout.getTabAt(1).isSelected()) {
            for (RestaurantEmployeeItemModel model : cooksList) {
                if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(model);
                }
            }
        } else {
            for (RestaurantEmployeeItemModel model : waiterList) {
                if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(model);
                }
            }
        }

        setFilteredList(filteredList);
    }

    private void setFilteredList(List<RestaurantEmployeeItemModel> models) {
        adapter.submitList(models);
    }

    private void initTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        adapter.submitList(basicList);
                        break;
                    case 1:
                        adapter.submitList(cooksList);
                        break;
                    case 2:
                        adapter.submitList(waiterList);
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

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof EmployeeState.Success) {
                List<EmployeeModel> employees = ((EmployeeState.Success)state).data;
                if (!employees.isEmpty()) {
                    initBasicList(employees);
                    initSubLists();
                    initRecycler();
                } else {
                    binding.progressBar.getRoot().setVisibility(View.GONE);
                    binding.constraintLayout.setVisibility(View.VISIBLE);
                }
            } else if (state instanceof EmployeeState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof EmployeeState.Error) {
                setErrorLayout();
            }
        });
    }

    private void initBasicList(List<EmployeeModel> employees) {
        for (int i = 0; i < employees.size(); i++) {
            EmployeeModel current = employees.get(i);
            basicList.add(new RestaurantEmployeeItemModel(i, current.getName(), current.getRole(), current.getId(), () -> {
                showDeleteRestaurantEmployeeDialog(current.getId(), current.getRole());
            }));
        }
    }

    private void setErrorLayout() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getEmployees(restaurantId, locationId);
        });
    }

    private void showDeleteRestaurantEmployeeDialog(String userId, String role) {
        DeleteRestaurantEmployeeDialogFragment dialogFragment = new DeleteRestaurantEmployeeDialogFragment(restaurantId, locationId, userId, role);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "DELETE_RESTAURANT_EMPLOYEE_DIALOG");
        dialogFragment.onDialogDismissedListener(bundle -> {
            removeItemFromList(userId, role);
        });
    }

    private void removeItemFromList(String userId, String role) {
        for (int i = 0; i < basicList.size(); i++) {
            if (basicList.get(i).getEmployeeId().equals(userId)) {
                basicList.remove(i);
                if (adapter.getCurrentList().equals(basicList)) {
                    adapter.notifyItemRemoved(i);
                }
                break;
            }
        }

        switch (role) {
            case COOK:
                for (int i = 0; i < cooksList.size(); i++) {
                    if (cooksList.get(i).getEmployeeId().equals(userId)) {
                        cooksList.remove(i);
                        if (adapter.getCurrentList().equals(cooksList)) {
                            adapter.notifyItemRemoved(i);
                        }
                        break;
                    }
                }
                break;
            case WAITER:
                for (int i = 0; i < waiterList.size(); i++) {
                    if (waiterList.get(i).getEmployeeId().equals(userId)) {
                        waiterList.remove(i);
                        if (adapter.getCurrentList().equals(waiterList)) {
                            adapter.notifyItemRemoved(i);
                        }
                        break;
                    }
                }
        }
    }

    private void initRecycler() {
        adapter.submitList(basicList);
        binding.recyclerView.setAdapter(adapter);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.GONE);
    }

    private void initSubLists() {
        for (int i = 0; i < basicList.size(); i++) {
            if (basicList.get(i).getRole().equals(WAITER)) {
                waiterList.add(basicList.get(i));
            } else {
                cooksList.add(basicList.get(i));
            }
        }
    }
}