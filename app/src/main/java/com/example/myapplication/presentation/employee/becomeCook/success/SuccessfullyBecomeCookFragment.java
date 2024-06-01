package com.example.myapplication.presentation.employee.becomeCook.success;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;

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
import com.example.myapplication.databinding.FragmentSuccessfullyBecomeCookBinding;

public class SuccessfullyBecomeCookFragment extends Fragment {

    private FragmentSuccessfullyBecomeCookBinding binding;
    private String companyId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyId = getArguments().getString(COMPANY_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSuccessfullyBecomeCookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInfoBox();
        initSeeDetails();
        handleBackButtonPressed();
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishActivity();
            }
        });
    }

    private void initSeeDetails() {
        binding.buttonSeeDetails.setOnClickListener(v -> {
           finishActivity();
        });
    }

    private void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra(EMPLOYEE_ROLE, COOK);
        intent.putExtra(COMPANY_ID, companyId);
        requireActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }


    private void initInfoBox() {
        binding.infoBoxLayout.body.setText(getString(R.string.now_you_can_view_active_orders_and_easily_monitor_the_preparation_of_dishes));
    }
}
