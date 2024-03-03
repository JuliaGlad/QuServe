package com.example.myapplication.presentation.profile.createAccount.firstFragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCreateAccountBinding;

/*
 * @author j.gladkikh
 */
public class CreateAccountFragment extends Fragment {

    private FragmentCreateAccountBinding binding;
    private CreateAccountViewModel viewModel;
    private String email, password, phoneNumber, userName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setupObserves();
        initLoginEditText();
        initSignUpButton();

    }

    private void initSignUpButton() {

        binding.signUpButton.setOnClickListener(v -> {

            userName = binding.nameEditText.getText().toString();
            phoneNumber = binding.phoneEditText.getText().toString().trim();
            email = binding.emailEditText.getText().toString().trim();
            password = binding.passwordEditText.getText().toString().trim();

            if (userName.isEmpty()) {
                viewModel.sendNameError(getResources().getString(R.string.user_name_is_required));
            }
            if (email.isEmpty()) {
                viewModel.sendEmailError(getResources().getString(R.string.email_is_required));
            }
            if (password.isEmpty()) {
                viewModel.sendPasswordError(getResources().getString(R.string.password_is_required));
            }

            if (!email.isEmpty() && !password.isEmpty() && !userName.isEmpty()) {
                viewModel.createUserWithEmailAndPassword(email, password, userName, phoneNumber);
                createVerificationDialog(email);

            }
        });
    }

    private void setupObserves() {

        viewModel.verified.observe(getViewLifecycleOwner(), verified -> {
            if (verified){
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_createAccount_to_chooseFragment);
            }
        });

        viewModel.emailError.observe(getViewLifecycleOwner(), errorMessage ->
                binding.emailCreateAccount.setError(errorMessage));

        viewModel.passwordError.observe(getViewLifecycleOwner(), errorMessage ->
                binding.passwordCreateAccount.setError(errorMessage));

        viewModel.nameError.observe(getViewLifecycleOwner(), errorMessage ->
                binding.nameCreateAccount.setError(errorMessage));
    }

    private void createVerificationDialog(String email) {

        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_verification_layout, null);
        AlertDialog verificationDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        verificationDialog.show();

        Button done_button = dialogView.findViewById(R.id.done_button);
        Button cancel_button = dialogView.findViewById(R.id.cancel_button);
        TextView yourEmail = dialogView.findViewById(R.id.your_email);
        yourEmail.setText(email);

        done_button.setOnClickListener(v -> {
                viewModel.checkVerification();
                verificationDialog.dismiss();
            });

        cancel_button.setOnClickListener(v -> {
            verificationDialog.dismiss();
            createFailureDialog();
        });
    }

    private void initLoginEditText() {
        binding.nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().isEmpty()) {
                    viewModel.sendNameError(getResources().getString(R.string.user_name_is_required));
                } else {
                    viewModel.removeNameError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().isEmpty()) {
                    viewModel.sendEmailError(getResources().getString(R.string.email_is_required));
                } else {
                    viewModel.removeEmailError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().isEmpty()) {
                    viewModel.sendPasswordError(getResources().getString(R.string.password_is_required));
                } else {
                    viewModel.removePasswordError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void createFailureDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_verification_failed, null);
        AlertDialog failedDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        failedDialog.show();

        Button okayButton = dialogView.findViewById(R.id.ok_button);
        okayButton.setOnClickListener(view -> {
            failedDialog.dismiss();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}