package com.example.myapplication.presentation.dialogFragments.aboutUs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.databinding.DialogAboutUsBinding;
import com.example.myapplication.databinding.DialogEmailUpdateSuccessfulBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AboutUsDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogAboutUsBinding binding = DialogAboutUsBinding.inflate(getLayoutInflater());

        binding.buttonOk.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }
}
