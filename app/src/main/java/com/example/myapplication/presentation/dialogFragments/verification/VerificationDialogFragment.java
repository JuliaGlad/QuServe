package com.example.myapplication.presentation.dialogFragments.verification;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogVerificationLayoutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.DialogDismissedListener;

public class VerificationDialogFragment extends DialogFragment {

    private VerificationDialogFragmentViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean isVerified;
    private final String email, password;

    public VerificationDialogFragment(String email, String password){
        this.password = password;
        this.email = email;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        viewModel = new ViewModelProvider(this).get(VerificationDialogFragmentViewModel.class);
        DialogVerificationLayoutBinding binding = DialogVerificationLayoutBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.userEmail.setText(email);

        binding.buttonDone.setOnClickListener(v -> {

            viewModel.isVerified();
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