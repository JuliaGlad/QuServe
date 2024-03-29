package com.example.myapplication.presentation.profile.profileLogin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.presentation.dialogFragments.checkEmail.CheckEmailDialogFragment;
import com.example.myapplication.presentation.dialogFragments.resetPassword.ResetPasswordDialogFragment;

import myapplication.android.ui.listeners.DialogDismissedListener;

/*
 * @author j.gladkikh
 */
public class ProfileLoginFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileLoginViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileLoginViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        viewModel.checkCurrentUser(this);
        setupObservers();
        initSignUpButton();
        initEditText();
        initLoginButton();
        initForgetPasswordButton();
    }

    private void initSignUpButton() {
        binding.textActionCreateAccount.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_profileLoggedFragment_to_createAccount);
        });
    }

    private void initForgetPasswordButton() {
        binding.forgetPassword.setOnClickListener(v -> {

            ResetPasswordDialogFragment dialogFragment = new ResetPasswordDialogFragment();

            dialogFragment.show(getActivity().getSupportFragmentManager(), "RESET_PASSWORD_DIALOG");
            DialogDismissedListener listener = (dialog) -> {
                CheckEmailDialogFragment checkDialogFragment = new CheckEmailDialogFragment();
                checkDialogFragment.show(getActivity().getSupportFragmentManager(), "CHECK_EMAIL_DIALOG");
            };
            dialogFragment.onDismissListener(listener);

        });
    }

    private void initLoginButton() {
        binding.buttonSignIn.setOnClickListener(v -> {
            String email = binding.editLayoutEmail.getText().toString().trim();
            String password = binding.editLayoutPassword.getText().toString().trim();
            if (email.isEmpty()) {
                viewModel.sendEmailError(getResources().getString(R.string.email_is_required));
            }
            if (password.isEmpty()) {
                viewModel.sendPasswordError(getResources().getString(R.string.password_is_required));
            }
            viewModel.signIn(email, password, this);
        });
    }

    private void setupObservers() {
        viewModel.emailError.observe(getViewLifecycleOwner(), errorMessage -> {
            binding.textLayoutEmail.setError(errorMessage);
        });

        viewModel.passwordError.observe(getViewLifecycleOwner(), errorMessage -> {
            binding.textLayoutPassword.setError(errorMessage);
        });
    }

    private void initEditText() {
        binding.editLayoutEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                viewModel.removeEmailError();
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
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                viewModel.removePasswordError();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
