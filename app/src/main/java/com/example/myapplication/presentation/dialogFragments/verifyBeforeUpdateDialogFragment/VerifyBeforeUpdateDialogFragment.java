package com.example.myapplication.presentation.dialogFragments.verifyBeforeUpdateDialogFragment;

import androidx.fragment.app.DialogFragment;
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
import com.example.myapplication.databinding.DialogVerificationLayoutBinding;
import com.example.myapplication.presentation.dialogFragments.verification.VerificationDialogFragmentViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class VerifyBeforeUpdateDialogFragment extends DialogFragment {

    private VerifyBeforeUpdateDialogViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean isVerified;
    private final String email, password;

    public VerifyBeforeUpdateDialogFragment(String email, String password){
        this.password = password;
        this.email = email;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        viewModel = new ViewModelProvider(this).get(VerifyBeforeUpdateDialogViewModel.class);
        DialogVerificationLayoutBinding binding = DialogVerificationLayoutBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.userEmail.setText(email);

        binding.buttonDone.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonDone.setEnabled(false);
            binding.buttonDone.setText("");
            viewModel.checkVerification(email, password);
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
        if (listener != null && isVerified) {
            listener.handleDialogClose(null);
        }
    }

    private void setupObserves(){
        viewModel.isVerified.observe(this, aBoolean -> {
            if (aBoolean){
                isVerified = true;
                dismiss();
            }
        });
    }
}