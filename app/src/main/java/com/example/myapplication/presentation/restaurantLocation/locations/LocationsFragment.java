package com.example.myapplication.presentation.restaurantLocation.locations;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
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
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeRestaurantLocationButton.HomeRestaurantLocationDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeRestaurantLocationButton.HomeRestaurantLocationModel;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonModel;
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
    private String restaurantId;
    private final List<RestaurantLocationModel> items = new ArrayList<>();
    private ActivityResultLauncher<Intent> addLocationLauncher;
    private final RestaurantLocationAdapter adapter = new RestaurantLocationAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LocationsViewModel.class);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        viewModel.getRestaurantLocations(restaurantId);
        initLauncher();
    }

    private void initLauncher() {
        addLocationLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        String city = result.getData().getStringExtra(CITY_KEY);
                        String location = result.getData().getStringExtra(LOCATION);
                        String locationId = result.getData().getStringExtra(LOCATION_ID);

                        items.add(new RestaurantLocationModel(
                                items.size(),
                                locationId,
                                location,
                                city,
                                "0",
                                "0",
                                () -> {
                                    ((LocationsActivity)requireActivity()).openLocationDetailsActivity(locationId);
                                }
                        ));
                        adapter.notifyItemInserted(items.size() - 1);
                    }
                });
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
        initBackButton();
        handleBackButtonPressed();
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void handleBackButtonPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            ((LocationsActivity)requireActivity()).openAddLocationActivity(addLocationLauncher);
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof LocationsState.Success) {
                List<LocationsModel> models = ((LocationsState.Success) state).data;
                if (!models.isEmpty()) {
                    initRecycler(models);
                } else {
                    binding.progressBar.getRoot().setVisibility(View.GONE);
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_locationsFragment_to_noLocationsYetFragment);
                }

            } else if (state instanceof LocationsState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof LocationsState.Error) {
                setError();
            }
        });
    }

    private void setError() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getRestaurantLocations(restaurantId);
        });
    }

    private void initRecycler(List<LocationsModel> models) {
        for (int i = 0; i < models.size(); i++) {
            LocationsModel current = models.get(i);
            items.add(new RestaurantLocationModel(
                    i,
                    current.getLocationId(),
                    current.getLocation(),
                    current.getCity(),
                    current.getWaitersCount(),
                    current.getCooksCount(),
                    () -> ((LocationsActivity)requireActivity()).openLocationDetailsActivity(current.getLocationId())
            ));
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(items);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }
}