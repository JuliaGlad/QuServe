package com.example.myapplication.presentation.restaurantMenu.addDish.dishSuccessfulyAdded;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDishAddedSuccessfulyBinding;

public class DishAddedSuccessfullyFragment extends Fragment {

    private FragmentDishAddedSuccessfulyBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDishAddedSuccessfulyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOkButton();
        initInfoBox();
        handleBackButtonPressed();
    }

    private void initInfoBox() {
        binding.infoBoxLayout.body.setText(getString(R.string.now_your_visitors_can_order_this_dish));
    }

    private void handleBackButtonPressed() {
        getActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    private void initOkButton() {
        binding.buttonSeeDetails.setOnClickListener(v -> {

        });
    }

}
