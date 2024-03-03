package com.example.myapplication.presentation.profile.loggedProfile.companyUser;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCompanyUserBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;

public class CompanyUserFragment extends Fragment {

    private CompanyUserViewModel viewModel;
    private FragmentCompanyUserBinding binding;
    private String companyId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyId = getArguments().getString("COMPANY_ID");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CompanyUserViewModel.class);
        viewModel.getQueue(companyId);
        binding = FragmentCompanyUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initToUser();
        initEmployees();
    }

    private void initEmployees() {
        binding.constraintLayoutEmployees.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openEmployeesActivity(companyId);
        });
    }

    private void initToUser() {
        binding.constraintLayoutToUser.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.logged_container, BasicUserFragment.class, null)
                    .commit();
        });
    }

    private void setupObserves() {
        viewModel.companyName.observe(getViewLifecycleOwner(), name -> {
            binding.companyName.setText(name);
        });
    }

}