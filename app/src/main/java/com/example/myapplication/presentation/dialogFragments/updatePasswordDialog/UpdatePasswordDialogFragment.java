package com.example.myapplication.presentation.dialogFragments.updatePasswordDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogUpdatePasswordLayoutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class UpdatePasswordDialogFragment extends DialogFragment {

    private UpdatePasswordDialogViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean dismiss;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        viewModel = new ViewModelProvider(this).get(UpdatePasswordDialogViewModel.class);
        DialogUpdatePasswordLayoutBinding binding = DialogUpdatePasswordLayoutBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.buttonSend.setOnClickListener(v -> {

            String oldPassword = binding.editLayoutOldPassword.getText().toString();
            String newPassword = binding.editLayoutNewPassword.getText().toString();

            viewModel.updatePassword(oldPassword, newPassword);
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
        if (listener != null && dismiss) {
            listener.handleDialogClose(null);
        }
    }

    private void setupObserves() {
        viewModel.dismiss.observe(this, aBoolean -> {
            dismiss = aBoolean;
            dismiss();
        });
    }
}