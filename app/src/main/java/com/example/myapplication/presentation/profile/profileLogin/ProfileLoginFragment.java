package com.example.myapplication.presentation.profile.profileLogin;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.BASIC;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.SIGNED_IN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.dialogFragments.checkEmail.CheckEmailDialogFragment;
import com.example.myapplication.presentation.dialogFragments.haveAnonymousActions.HaveActionsDialogFragment;
import com.example.myapplication.presentation.dialogFragments.resetPassword.ResetPasswordDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.CompanyUserFragment;
import com.google.android.material.snackbar.Snackbar;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class ProfileLoginFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileLoginViewModel viewModel;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileLoginViewModel.class);
        setLauncher();
    }

    private void setLauncher() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {

                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                        String state = sharedPreferences.getString(APP_STATE, ANONYMOUS);

                        switch (state){
                            case COMPANY:
                            case RESTAURANT:
                                requireActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .setReorderingAllowed(true)
                                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                                        .replace(R.id.logged_container, CompanyUserFragment.class, null)
                                        .commit();
                                break;
                            case BASIC:
                                requireActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .setReorderingAllowed(true)
                                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                                        .replace(R.id.logged_container, BasicUserFragment.class, null)
                                        .commit();
                                break;
                        }
                    }
                });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupObservers();
        initSignUpButton();
        initEditText();
        initLoginButton();
        initForgetPasswordButton();
    }

    private void initSignUpButton() {
        binding.textActionCreateAccount.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openCreateAccountActivity(launcher);
        });
    }

    private void initForgetPasswordButton() {
        binding.forgetPassword.setOnClickListener(v -> {

            ResetPasswordDialogFragment dialogFragment = new ResetPasswordDialogFragment();

            dialogFragment.show(requireActivity().getSupportFragmentManager(), "RESET_PASSWORD_DIALOG");
            DialogDismissedListener listener = (dialog) -> {
                CheckEmailDialogFragment checkDialogFragment = new CheckEmailDialogFragment();
                checkDialogFragment.show(requireActivity().getSupportFragmentManager(), "CHECK_EMAIL_DIALOG");
            };
            dialogFragment.onDismissListener(listener);

        });
    }

    private void initLoginButton() {
        binding.buttonSignIn.setOnClickListener(v -> {
            if (viewModel.checkAnonymousActionsUseCase()) {
                binding.buttonSignIn.setEnabled(false);
                binding.loader.setVisibility(View.VISIBLE);
                String email = binding.editLayoutEmail.getText().toString().trim();
                String password = binding.editLayoutPassword.getText().toString().trim();
                if (email.isEmpty()) {
                    binding.buttonSignIn.setEnabled(true);
                    binding.loader.setVisibility(View.GONE);
                    viewModel.sendEmailError(getResources().getString(R.string.email_is_required));
                }
                if (password.isEmpty()) {
                    binding.buttonSignIn.setEnabled(true);
                    binding.loader.setVisibility(View.GONE);
                    viewModel.sendPasswordError(getResources().getString(R.string.password_is_required));
                }
                if (!email.isEmpty() && !password.isEmpty()){
                    viewModel.signIn(email, password);
                }

            } else {
                binding.buttonSignIn.setEnabled(true);
                binding.loader.setVisibility(View.GONE);
                HaveActionsDialogFragment dialogFragment = new HaveActionsDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "HAVE_ACTIONS_DIALOG");
            }
        });
    }

    private void setupObservers() {

        viewModel.signedIn.observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result.equals(SIGNED_IN)) {
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString(APP_STATE, BASIC).apply();

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                    fragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                            .setReorderingAllowed(true)
                            .replace(R.id.logged_container, BasicUserFragment.class, null)
                            .commit();

                    binding.buttonSignIn.setEnabled(true);
                    binding.loader.setVisibility(View.GONE);
                } else {
                    binding.buttonSignIn.setEnabled(true);
                    binding.loader.setVisibility(View.GONE);

                    Snackbar.make(requireView(), getText(R.string.wrong_credentials), Snackbar.LENGTH_LONG).show();
                }
            }
        });

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
