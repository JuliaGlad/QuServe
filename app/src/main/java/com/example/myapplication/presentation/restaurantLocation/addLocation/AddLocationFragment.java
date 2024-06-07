package com.example.myapplication.presentation.restaurantLocation.addLocation;

import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.FINE_PERMISSION_CODE;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_LOCATION_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAddLocationBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.chooseLocation.LocationDelegate;
import myapplication.android.ui.recycler.ui.items.items.chooseLocation.LocationDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.chooseLocation.LocationModel;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;

public class AddLocationFragment extends Fragment {

    private AddLocationViewModel viewModel;
    private FragmentAddLocationBinding binding;
    private String location, city, restaurantId;
    private final MainAdapter adapter = new MainAdapter();
    private final List<DelegateItem> itemList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddLocationViewModel.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddLocationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setAdapter();
        initBackButton();
        initRecycler();
        initAddButton();
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setAdapter() {
        adapter.addDelegate(new TextViewHeaderDelegate());
        adapter.addDelegate(new LocationDelegate());
        binding.recyclerView.setAdapter(adapter);
    }

    private void setupObserves() {
        viewModel.isAdded.observe(getViewLifecycleOwner(), locationId -> {
            if (locationId != null){
                Intent intent = new Intent();
                intent.putExtra(LOCATION_ID, locationId);
                intent.putExtra(LOCATION, location);
                intent.putExtra(CITY_KEY, city);
                requireActivity().setResult(Activity.RESULT_OK, intent);
                requireActivity().finish();
            }
        });
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            if (location != null) {
                binding.loader.setVisibility(View.VISIBLE);
                binding.buttonAdd.setEnabled(false);
                viewModel.addLocation(restaurantId, location, city);
            } else {
                Snackbar.make(requireView(), getString(R.string.this_data_is_required), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void initRecycler() {
        try{
            location = getArguments().getString(QUEUE_LOCATION_KEY);
            city = getArguments().getString(CITY_KEY);
        } catch (NullPointerException e){
            Log.i("NullPointerException", e.getMessage());
        }
        itemList.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.choose_queue_location, 24)));
        itemList.add(new LocationDelegateItem(new LocationModel(2, location, () -> {

            if (checkSelfMapPermission()) {
                Bundle bundle = new Bundle();
                bundle.putString(STATE, LOCATION);
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_addLocationFragment_to_mapFragment, bundle);
            }
        })));
        adapter.submitList(itemList);
    }

    private boolean checkSelfMapPermission() {
        boolean permission = false;
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        } else {
            permission = true;
        }
        return permission;
    }
}