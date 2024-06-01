package com.example.myapplication.presentation.employee.becomeEmployee;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_DATA;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBecomeEmployeeBinding;

public class BecomeEmployeeFragment extends Fragment {

    private BecomeEmployeeViewModel viewModel;
    private FragmentBecomeEmployeeBinding binding;
    private String companyId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(BecomeEmployeeViewModel.class);
        binding = FragmentBecomeEmployeeBinding.inflate(inflater, container, false);

        try {
            companyId = requireActivity().getIntent().getStringExtra(EMPLOYEE_DATA);
        }catch (NullPointerException e){
            requireActivity().finish();
        }

        if (companyId != null) {
            binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            viewModel.getCompany(companyId);
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
            viewModel.addEmployee(companyId);
        });
    }

    private void initNoButton() {
        binding.buttonNo.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {

        viewModel.isEmployee.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, companyId);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_becomeEmployeeFragment_to_successfullyBecomeEmployeeFragment, bundle);
            }
        });

        viewModel.companyName.observe(getViewLifecycleOwner(), string -> {
            binding.companyName.setText(string);
        });

        viewModel.image.observe(getViewLifecycleOwner(), uri -> {
            if (!uri.equals(Uri.EMPTY)) {
                Glide.with(requireContext())
                        .load(uri)
                        .into(binding.qrCodeImage);
                binding.progressLayout.getRoot().setVisibility(View.GONE);
                binding.errorLayout.getRoot().setVisibility(View.GONE);
            } else {
                binding.progressLayout.getRoot().setVisibility(View.GONE);
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getCompany(companyId);
        });
    }
}