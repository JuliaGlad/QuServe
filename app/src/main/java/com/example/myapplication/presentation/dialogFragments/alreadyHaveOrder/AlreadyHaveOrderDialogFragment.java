package com.example.myapplication.presentation.dialogFragments.alreadyHaveOrder;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.databinding.DialogYouAlreadyHaveOrderBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AlreadyHaveOrderDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DialogYouAlreadyHaveOrderBinding binding = DialogYouAlreadyHaveOrderBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        binding.buttonOk.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }
}
