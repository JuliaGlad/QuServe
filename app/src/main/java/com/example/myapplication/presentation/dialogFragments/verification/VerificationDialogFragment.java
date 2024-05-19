package com.example.myapplication.presentation.dialogFragments.verification;

import static com.example.myapplication.presentation.utils.Utils.IS_VERIFIED;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogVerificationLayoutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class VerificationDialogFragment extends DialogFragment {
    private DialogVerificationLayoutBinding binding;
    private VerificationDialogFragmentViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean isVerified;
    private final String email, password;

    public VerificationDialogFragment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        viewModel = new ViewModelProvider(this).get(VerificationDialogFragmentViewModel.class);
        binding = DialogVerificationLayoutBinding.inflate(getLayoutInflater());
        setupObserves();
        binding.userEmail.setText(email);
        binding.buttonDone.setOnClickListener(v -> {
            viewModel.isVerified();
        });
        binding.buttonCancel.setOnClickListener(v -> {
            viewModel.deleteNotVerifiedAccount(password);
        });
        setCancelable(false);
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

    private void setupObserves() {
        viewModel.isVerified.observe(this, aBoolean -> {
            if (aBoolean) {
                isVerified = true;
                dismiss();
            } else {
                Snackbar.make(binding.getRoot(), getString(R.string.email_is_not_verified), Snackbar.LENGTH_LONG).show();
            }
        });

        viewModel.isDeleted.observe(this, aBoolean -> {
            if (aBoolean){
                dismiss();
            }
        });
    }

}