package com.example.myapplication.presentation.dialogFragments.employeeQrCode;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.DialogEmployeeQrCodeBinding;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class EmployeeQrCodeDialogFragment extends DialogFragment {

    private EmployeeQrCodeViewModel viewModel;
    private DialogEmployeeQrCodeBinding binding;
    private final String companyId;

    public EmployeeQrCodeDialogFragment(String companyId){
        this.companyId = companyId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        binding = DialogEmployeeQrCodeBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(EmployeeQrCodeViewModel.class);

        setupObserves();

        viewModel.getQrCode(companyId);

        binding.buttonOk.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.image.observe(this, uri -> {
            if (uri != Uri.EMPTY) {
                Glide.with(requireContext())
                        .load(uri)
                        .into(binding.qrCode);
            }
        });
    }
}