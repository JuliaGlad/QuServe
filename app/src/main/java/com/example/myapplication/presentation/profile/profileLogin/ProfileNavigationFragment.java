package com.example.myapplication.presentation.profile.profileLogin;

import android.app.AlertDialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileBinding;

/*
 * @author j.gladkikh
 */
public class ProfileNavigationFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileNavigationViewModel viewModel;
    private EditText emailForgetPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileNavigationViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        viewModel.checkCurrentUser(this);
        setupObservers();
        initLoginEditLayouts();
        initLoginButton();
        initForgetPasswordButton();

        binding.letGetStarted.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_navigation_profile_to_createAccount);
        });

        binding.skip.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_navigation_profile_to_navigation_home);
        });

    }

    private void initForgetPasswordButton() {
        binding.forgetPassword.setOnClickListener(v -> {
            showResetPasswordDialog();
        });
    }

    private void initLoginButton() {
        binding.loginButton.setOnClickListener(v -> {
            viewModel.showProgress(true);
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();
            if (email.isEmpty()) {
                viewModel.sendEmailError(getResources().getString(R.string.email_is_required));
                viewModel.showProgress(false);
            }
            if (password.isEmpty()) {
                viewModel.sendPasswordError(getResources().getString(R.string.password_is_required));
                viewModel.showProgress(false);
            }
            viewModel.signIn(email, password, this);
        });
    }

    private void initLoginEditLayouts() {
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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

    private void showResetPasswordDialog(){
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_reset_password_layout, null);
        AlertDialog passwordResetDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        Button send = dialogView.findViewById(R.id.send_button);
        Button cancel = dialogView.findViewById(R.id.cancel_button);
        emailForgetPassword = dialogView.findViewById(R.id.edit_email_dialog);

        setupResetPasswordObserves();

        send.setOnClickListener(view -> {
            String checkingEmail = emailForgetPassword.getText().toString();

            if (checkingEmail.isEmpty()) {
                viewModel.sendResetEmailError(getResources().getString(R.string.this_field_is_required));
                passwordResetDialog.dismiss();
            }
            viewModel.sendResetPasswordEmail(checkingEmail);
        });

        cancel.setOnClickListener(cancelView -> {
            passwordResetDialog.dismiss();
        });

        passwordResetDialog.show();
    }

    private void setupObservers() {
        viewModel.emailError.observe(getViewLifecycleOwner(), errorMessage -> {
            binding.emailLoginTxtInputLayout.setError(errorMessage);
        });

        viewModel.passwordError.observe(getViewLifecycleOwner(), errorMessage -> {
            binding.passwordLoginTxtInputLayout.setError(errorMessage);
        });

        viewModel.showProgress.observe(getViewLifecycleOwner(), show -> {
            if (show) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void setupResetPasswordObserves(){
        viewModel.resetEmailError.observe(getViewLifecycleOwner(), errorMessage -> {
            emailForgetPassword.setError(errorMessage);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
