package com.example.myapplication.presentation.dialogFragments.emailUpdateSuccessful;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.databinding.DialogEmailUpdateSuccessfulBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class EmailUpdateSuccessfulDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogEmailUpdateSuccessfulBinding binding = DialogEmailUpdateSuccessfulBinding.inflate(getLayoutInflater());

        binding.buttonOk.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }
}
