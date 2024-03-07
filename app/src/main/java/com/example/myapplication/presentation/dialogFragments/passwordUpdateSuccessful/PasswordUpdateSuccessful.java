package com.example.myapplication.presentation.dialogFragments.passwordUpdateSuccessful;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.databinding.DialogPasswordUpdateSuccessfulBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PasswordUpdateSuccessful extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogPasswordUpdateSuccessfulBinding binding = DialogPasswordUpdateSuccessfulBinding.inflate(getLayoutInflater());

        binding.buttonOk.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }
}
