package com.example.myapplication.presentation.restaurantLocation.noLocationsYet;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNoLocationsYetBinding;
import com.example.myapplication.presentation.restaurantLocation.LocationsActivity;

public class NoLocationsYetFragment extends Fragment {

    private FragmentNoLocationsYetBinding binding;
    private ActivityResultLauncher<Intent> addLocationLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLauncher();
    }

    private void initLauncher() {
        addLocationLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        NavHostFragment.findNavController(this)
                                .navigate(R.id.action_noLocationsYetFragment_to_locationsFragment);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentNoLocationsYetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInfoBox();
        initAddButton();
        initBackButton();
        handleBackButtonPressed();
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            ((LocationsActivity)requireActivity()).openAddLocationActivity(addLocationLauncher);
        });
    }

    private void initInfoBox() {
        binding.infoBox.body.setText(getString(R.string.create_your_first_location_and_assign_employees));
    }
}
