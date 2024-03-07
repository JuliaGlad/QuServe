package com.example.myapplication.presentation.dialogFragments.resetPassword;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.myapplication.databinding.DialogResetPasswordLayoutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.DialogDismissedListener;

public class ResetPasswordDialogFragment extends DialogFragment {

    private DialogResetPasswordLayoutBinding binding;
    private ResetPasswordDialogViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean isSend = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        binding = DialogResetPasswordLayoutBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(ResetPasswordDialogViewModel.class);

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        binding.buttonSend.setOnClickListener(v -> {

            initEditText();

            String email = binding.editLayoutEmail.getText().toString();
            if (!email.isEmpty()) {
                viewModel.sendResetPasswordEmail(email)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new SingleObserver<Boolean>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Boolean aBoolean) {
                                isSend = aBoolean;
                                dismiss();
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        });

            } else {
                binding.editLayoutEmail.setError("This field is required");
            }
        });

        binding.buttonCancel.setOnClickListener(v -> dismiss());

        return builder.setView(binding.getRoot()).create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }

    private void initEditText() {
        binding.editLayoutEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text != null && !text.toString().isEmpty() ) {
                    viewModel.removeEmailError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isSend) {
            listener.handleDialogClose(null);
        }
    }

    private void setupObserves() {
        viewModel.emailError.observe(getViewLifecycleOwner(), message -> {
            binding.editLayoutEmail.setError(message);
        });
    }
}