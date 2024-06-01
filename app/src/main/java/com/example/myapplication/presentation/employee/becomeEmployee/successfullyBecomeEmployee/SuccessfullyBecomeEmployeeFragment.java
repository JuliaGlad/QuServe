package com.example.myapplication.presentation.employee.becomeEmployee.successfullyBecomeEmployee;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSuccessfulyBecomeEmployeeBinding;

public class SuccessfullyBecomeEmployeeFragment extends Fragment {

    private FragmentSuccessfulyBecomeEmployeeBinding binding;
    private String companyId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSuccessfulyBecomeEmployeeBinding.inflate(inflater, container, false);
        binding.infoBoxLayout.body.setText(getResources().getString(R.string.now_you_can_manage_given_to_you_companies_queues));
        companyId = getArguments().getString(COMPANY_ID);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handleButtonBackPressed();
        initSeeDetailsButton();
    }

    private void initSeeDetailsButton() {
        binding.buttonSeeDetails.setOnClickListener(v -> finishActivity());
    }

    private void handleButtonBackPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishActivity();
            }
        });
    }

    private void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra(EMPLOYEE_ROLE, WORKER);
        intent.putExtra(COMPANY_ID, companyId);
        requireActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }
}