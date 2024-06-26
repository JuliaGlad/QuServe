package com.example.myapplication.presentation.common.orderDetails.orderIsFinished;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentOrderIsFinishedBinding;

public class OrderIsFinishedFragment extends Fragment {

    private FragmentOrderIsFinishedBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderIsFinishedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOkButton();
        initInfoLayout();
        initBackButton();
        handleBackButtonPressed();
    }

    private void initInfoLayout() {
        binding.infoBoxLayout.body.setText(getString(R.string.our_cooks_finished_your_order_and_it_should_be_served_to_you_soon));
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().setResult(Activity.RESULT_OK);
                requireActivity().finish();
            }
        });
    }


    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().setResult(Activity.RESULT_OK);
            requireActivity().finish();
        });
    }

    private void initOkButton() {
        binding.buttonOk.setOnClickListener(v -> {
            requireActivity().setResult(Activity.RESULT_OK);
            requireActivity().finish();
        });
    }
}