package com.example.myapplication.presentation.employee.becomeEmployee.successfullyBecomeEmployee;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSuccessfulyBecomeEmployeeBinding;

public class SuccessfullyBecomeEmployeeFragment extends Fragment {

    private FragmentSuccessfulyBecomeEmployeeBinding binding;
    private String companyId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        requireActivity().finish();
    }

    private void handleButtonBackPressed(){
        getActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }
}