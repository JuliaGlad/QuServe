package com.example.myapplication.presentation.companyQueue.createQueue.map;


import static com.example.myapplication.presentation.utils.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.PAGE_3;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LOCATION_KEY;
import static com.example.myapplication.presentation.utils.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMapBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private MapViewModel viewModel;
    private FragmentMapBinding binding;
    private String location, city, state;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = getArguments().getString(STATE);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserves();

        initBottomSheetBehaviour();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        viewModel.getLastLocation(MapFragment.this, getActivity()).addOnSuccessListener(location -> {
            mapFragment.getMapAsync(this);
        });


        initMapSearchBar();
        initFloatingLocationButton();
        initChooseBottomSheetButton();
        //TODO initCancelBottomSheetButton(){}
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        viewModel.googleMap = googleMap;
        viewModel.setLastLocationMarker();
        binding.addressMainName.setText(getResources().getString(R.string.current_location));

    }

    private void initChooseBottomSheetButton() {
        binding.chooseButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            switch (state){
                case COMPANY:
                    bundle.putString(PAGE_KEY, PAGE_3);
                    bundle.putString(QUEUE_LOCATION_KEY, location);
                    bundle.putString(CITY_KEY, city);
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_mapFragment_to_createCompanyQueueFragment, bundle);
                    break;
                case LOCATION:
                    bundle.putString(QUEUE_LOCATION_KEY, location);
                    bundle.putString(CITY_KEY, city);
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_mapFragment_to_addLocationFragment, bundle);
                    break;
            }


        });
    }

    private void initFloatingLocationButton() {
        binding.floatingMyLocationButton.setOnClickListener(v -> {
            viewModel.getLastLocation(MapFragment.this, getActivity()).addOnSuccessListener(
                    location -> {
                        viewModel.setLastLocationMarker();
                    }
            );
        });
    }

    private void initMapSearchBar() {
        binding.mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = binding.mapSearchView.getQuery().toString();
                viewModel.searchForTheLocation(location, MapFragment.this);
                binding.addressMainName.setText(location);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initBottomSheetBehaviour() {
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(70);
    }

    private void setupObserves() {
        viewModel.addressLine.observe(getViewLifecycleOwner(), string -> {
            location = string;
            binding.addressLine.setText(string);
        });

        viewModel.cityName.observe(getViewLifecycleOwner(), string -> {
            city = string;
            binding.addressMainName.setText(string);
        });

    }
}