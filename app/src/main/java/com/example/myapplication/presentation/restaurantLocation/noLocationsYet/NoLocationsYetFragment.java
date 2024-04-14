package com.example.myapplication.presentation.restaurantLocation.noLocationsYet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNoLocationsYetBinding;

public class NoLocationsYetFragment extends Fragment {

    FragmentNoLocationsYetBinding binding;

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
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_noLocationsYetFragment_to_addLocationFragment);
        });
    }

    private void initInfoBox() {
        binding.infoBox.body.setText(getString(R.string.create_your_first_location_and_assign_employees));
    }
}
