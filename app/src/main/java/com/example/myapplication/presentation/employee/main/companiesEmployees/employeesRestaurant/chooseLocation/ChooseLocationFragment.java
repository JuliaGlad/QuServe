package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.chooseLocation;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentChooseLocationBinding;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.chooseLocation.state.ChooseLocationState;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.chooseLocation.state.ChooseLocationStateModel;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.main.EmployeesRestaurantFragment;
import com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.recycler.RestaurantEmployeeItemAdapter;
import com.example.myapplication.presentation.restaurantLocation.LocationsActivity;
import com.example.myapplication.presentation.restaurantLocation.locations.model.LocationsModel;
import com.example.myapplication.presentation.restaurantLocation.locations.recycler.RestaurantLocationAdapter;
import com.example.myapplication.presentation.restaurantLocation.locations.recycler.RestaurantLocationModel;
import com.example.myapplication.presentation.restaurantLocation.locations.state.LocationsState;

import java.util.ArrayList;
import java.util.List;

public class ChooseLocationFragment extends Fragment {

    private ChooseLocationViewModel viewModel;
    private FragmentChooseLocationBinding binding;
    private String restaurantId;
    private final RestaurantLocationAdapter adapter = new RestaurantLocationAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ChooseLocationViewModel.class);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        viewModel.getRestaurantLocations(restaurantId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChooseLocationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ChooseLocationState.Success) {
                List<ChooseLocationStateModel> models = ((ChooseLocationState.Success)state).data;
                if (!models.isEmpty()) {
                    initRecycler(models);
                } else {
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_locationsFragment_to_noLocationsYetFragment);
                }

            } else if (state instanceof ChooseLocationState.Loading) {
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof ChooseLocationState.Error) {
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getRestaurantLocations(restaurantId);
        });
    }

    private void initRecycler(List<ChooseLocationStateModel> models) {
        List<RestaurantLocationModel> items = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            ChooseLocationStateModel current = models.get(i);
            String locationId = current.getLocationId();
            items.add(new RestaurantLocationModel(
                    i,
                    locationId,
                    current.getLocation(),
                    current.getCity(),
                    current.getWaitersCount(),
                    current.getCooksCount(),
                    () -> {
                        Bundle bundle = new Bundle();
                        bundle.putString(LOCATION_ID, locationId);
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.employee_nav_container, EmployeesRestaurantFragment.class, bundle)
                                .commit();
                    }
            ));
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(items);
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

}