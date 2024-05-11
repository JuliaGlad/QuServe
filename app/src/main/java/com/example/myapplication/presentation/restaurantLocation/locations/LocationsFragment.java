package com.example.myapplication.presentation.restaurantLocation.locations;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

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
import com.example.myapplication.databinding.FragmentLocationsBinding;
import com.example.myapplication.presentation.restaurantLocation.LocationsActivity;
import com.example.myapplication.presentation.restaurantLocation.locations.model.LocationsModel;
import com.example.myapplication.presentation.restaurantLocation.locations.recycler.RestaurantLocationAdapter;
import com.example.myapplication.presentation.restaurantLocation.locations.recycler.RestaurantLocationModel;
import com.example.myapplication.presentation.restaurantLocation.locations.state.LocationsState;

import java.util.ArrayList;
import java.util.List;

public class LocationsFragment extends Fragment {

    private LocationsViewModel viewModel;
    private FragmentLocationsBinding binding;
    private RestaurantLocationAdapter adapter = new RestaurantLocationAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LocationsViewModel.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        viewModel.getRestaurantLocations(restaurantId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLocationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initAddButton();
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            ((LocationsActivity)requireActivity()).openAddLocationActivity();
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof LocationsState.Success) {
                List<LocationsModel> models = ((LocationsState.Success) state).data;
                if (models.size() > 0) {
                    initRecycler(models);
                } else {
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_locationsFragment_to_noLocationsYetFragment);
                }

            } else if (state instanceof LocationsState.Loading) {

            } else if (state instanceof LocationsState.Error) {

            }
        });
    }

    private void initRecycler(List<LocationsModel> models) {
        List<RestaurantLocationModel> items = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            LocationsModel current = models.get(i);
            items.add(new RestaurantLocationModel(
                    i,
                    current.getLocationId(),
                    current.getLocation(),
                    current.getCity(),
                    current.getWaitersCount(),
                    current.getCooksCount(),
                    () -> {
                        ((LocationsActivity)requireActivity()).openLocationDetailsActivity(current.getLocationId());
                    }
            ));
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(items);
    }
}