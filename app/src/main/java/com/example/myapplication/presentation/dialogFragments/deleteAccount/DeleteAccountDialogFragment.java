package com.example.myapplication.presentation.dialogFragments.deleteAccount;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogDeleteAccountBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class DeleteAccountDialogFragment extends DialogFragment {

    private DeleteAccountDialogViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean isDeleted = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        viewModel = new ViewModelProvider(this).get(DeleteAccountDialogViewModel.class);
        DialogDeleteAccountBinding binding = DialogDeleteAccountBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            String password = binding.editLayoutPassword.getText().toString();
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonDelete.setEnabled(false);
            binding.buttonDelete.setText("");
            viewModel.deleteAccount(password);
        });

        binding.buttonCancel.setOnClickListener(v -> dismiss());

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.isDeleted.observe(this, aBoolean -> {
            isDeleted = aBoolean;
            dismiss();
        });
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isDeleted) {
            listener.handleDialogClose(null);
        }
    }
}