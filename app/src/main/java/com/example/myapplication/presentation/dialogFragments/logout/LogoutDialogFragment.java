package com.example.myapplication.presentation.dialogFragments.logout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogLogoutLayoutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class LogoutDialogFragment extends DialogFragment {

    private LogoutDialogViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean isLogout = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        viewModel = new ViewModelProvider(this).get(LogoutDialogViewModel.class);
        DialogLogoutLayoutBinding binding = DialogLogoutLayoutBinding.inflate(getLayoutInflater());

        binding.buttonLogout.setOnClickListener(v -> {
            viewModel.logout();
            isLogout = true;
            dismiss();
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isLogout){
            listener.handleDialogClose(null);
        }
    }

}