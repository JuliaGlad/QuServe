package com.example.myapplication.presentation.dialogFragments.deleteAccount;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogDeleteLayoutBinding;
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
        DialogDeleteLayoutBinding binding = DialogDeleteLayoutBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            String password = null;
            viewModel.deleteAccount(password);
            dismiss();
        });

        binding.buttonCancel.setOnClickListener(v -> dismiss());

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.isDeleted.observe(this, aBoolean -> {
            isDeleted = aBoolean;
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