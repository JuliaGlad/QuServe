package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.orderIsFinishedCook;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCookOrderIsFinishedBinding;

public class CookOrderIsFinishedFragment extends Fragment {

    private FragmentCookOrderIsFinishedBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCookOrderIsFinishedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOkayButton();
        initInfoBox();
        initButtonBack();
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

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void initInfoBox() {
        binding.infoBoxLayout.body.setText(getString(R.string.you_can_now_continue_doing_other_order_and_this_one_will_be_served_soon));
    }

    private void initOkayButton() {
        binding.buttonOk.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }
}