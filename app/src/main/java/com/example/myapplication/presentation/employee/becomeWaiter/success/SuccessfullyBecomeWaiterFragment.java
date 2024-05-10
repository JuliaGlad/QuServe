package com.example.myapplication.presentation.employee.becomeWaiter.success;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSuccessfullyBecomeWaiterBinding;

public class SuccessfullyBecomeWaiterFragment extends Fragment {

    private FragmentSuccessfullyBecomeWaiterBinding binding;
    private String companyId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSuccessfullyBecomeWaiterBinding.inflate(inflater, container, false);
        companyId = getArguments().getString(COMPANY_ID);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInfoBox();
        initSeeDetails();
        handleBackButtonPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    private void initSeeDetails() {
        binding.buttonSeeDetails.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(COMPANY_ID, companyId);
            intent.putExtra(EMPLOYEE_ROLE, WAITER);
            requireActivity().setResult(Activity.RESULT_OK, intent);
            requireActivity().finish();
        });
    }

    private void initInfoBox() {
        binding.infoBoxLayout.body.setText(getString(R.string.now_you_can_get_access_to_restaurant_orders_and_serve_dishes));
    }
}
