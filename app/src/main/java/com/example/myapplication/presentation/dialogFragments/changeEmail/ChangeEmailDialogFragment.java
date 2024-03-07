package com.example.myapplication.presentation.dialogFragments.changeEmail;

import static com.example.myapplication.presentation.utils.Utils.EMAIL;
import static com.example.myapplication.presentation.utils.Utils.PASSWORD;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.myapplication.databinding.DialogUpdateEmailLayoutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class ChangeEmailDialogFragment extends DialogFragment {

    private ChangeEmailDialogViewModel viewModel;
    private DialogUpdateEmailLayoutBinding binding;
    private DialogDismissedListener listener;
    private String email, password;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        viewModel = new ViewModelProvider(this).get(ChangeEmailDialogViewModel.class);
        binding = DialogUpdateEmailLayoutBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.buttonCancel.setOnClickListener(v -> dismiss());

        binding.buttonSend.setOnClickListener(v -> {

            initEditText();

            email = binding.editLayoutNewEmail.getText().toString();
            password = binding.editLayoutPassword.getText().toString();
            if (email.isEmpty()) {
                binding.editLayoutNewEmail.setError("This field is required!");
            }
            if (password.isEmpty()) {
                binding.editLayoutPassword.setError("This field is required!");
            }

            if (!email.isEmpty() && !password.isEmpty()) {
                viewModel.checkUser(password);
            }
        });

        return builder.setView(binding.getRoot()).create();
    }

    public void setupObserves() {
        viewModel.dismissDialog.observe(this, aBoolean -> {
            if (aBoolean) {
                dismiss();
            }
        });

    }

    private void initEditText() {
        binding.editLayoutNewEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    binding.editLayoutNewEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.editLayoutPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    binding.editLayoutPassword.setError(null);
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
        if (listener != null) {
            Bundle bundle = new Bundle();
            bundle.putString(EMAIL, email);
            bundle.putString(PASSWORD, password);
            listener.handleDialogClose(bundle);
        }
    }
}