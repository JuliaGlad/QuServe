package com.example.myapplication.presentation.profile.becomeEmployee;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBecomeEmployeeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;

public class BecomeEmployeeFragment extends Fragment {

    private BecomeEmployeeViewModel viewModel;
    private FragmentBecomeEmployeeBinding binding;
    private String companyData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(BecomeEmployeeViewModel.class);
        binding = FragmentBecomeEmployeeBinding.inflate(inflater, container, false);

        companyData = getActivity().getIntent().getStringExtra(COMPANY);

        if (companyData != null) {
           viewModel.getCompany(companyData);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setupObserves();
        initNoButton();
        initYesButton();
    }

    private void initYesButton() {
        binding.buttonYes.setOnClickListener(v -> {
            viewModel.addEmployee(companyData);
        });
    }

    private void initNoButton() {
        binding.buttonNo.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {
        viewModel.companyName.observe(getViewLifecycleOwner(), string -> {
            binding.companyName.setText(string);
        });

        viewModel.image.observe(getViewLifecycleOwner(), string -> {
            string.addOnCompleteListener(task -> {
                Glide.with(requireContext()).load(task.getResult()).into(binding.qrCodeImage);
            });
        });
    }
}