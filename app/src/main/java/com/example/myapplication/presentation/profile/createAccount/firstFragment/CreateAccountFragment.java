package com.example.myapplication.presentation.profile.createAccount.firstFragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

import myapplication.android.ui.listeners.DialogDismissedListener;

import com.example.myapplication.databinding.DialogHaveAnonymousActionsBinding;
import com.example.myapplication.databinding.FragmentCreateAccountBinding;
import com.example.myapplication.presentation.dialogFragments.haveAnonymousActions.HaveAnonymousActionsDialogFragment;
import com.example.myapplication.presentation.dialogFragments.verification.VerificationDialogFragment;
import com.example.myapplication.presentation.profile.profileLogin.ProfileLoginFragment;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;

public class CreateAccountFragment extends Fragment {

    private FragmentCreateAccountBinding binding;
    private CreateAccountViewModel viewModel;
    private String email, password, userName;
    private Uri imageUri = null;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityResultLauncher();
        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupObserves();
        initSignUpButton();
        initEditText();
        initAddPhoto();
       initButtonBack();
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
           requireActivity().finish();
        });
    }

    private void initAddPhoto() {
        binding.addPhotoLayout.setOnClickListener(v -> {
            initImagePicker();
        });
    }

    private void initSignUpButton() {
        binding.buttonSignUp.setOnClickListener(v -> {

            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonSignUp.setEnabled(false);

            userName = binding.editLayoutName.getText().toString();
            email = binding.editLayoutEmail.getText().toString().trim();
            password = binding.editLayoutPassword.getText().toString().trim();

            if (userName.isEmpty()) {
                viewModel.sendNameError(getResources().getString(R.string.user_name_is_required));
            }
            if (email.isEmpty()) {
                viewModel.sendEmailError(getResources().getString(R.string.email_is_required));
            }
            if (password.isEmpty()) {
                viewModel.sendPasswordError(getResources().getString(R.string.password_is_required));
            }

            if (viewModel.checkAnonymousActions()) {
                if (!email.isEmpty() && !password.isEmpty() && !userName.isEmpty()) {
                    viewModel.createUserWithEmailAndPassword(email, password, userName, imageUri);
                } else {
                    binding.loader.setVisibility(View.VISIBLE);
                    binding.buttonSignUp.setEnabled(false);
                }
            } else {
                binding.loader.setVisibility(View.VISIBLE);
                binding.buttonSignUp.setEnabled(false);
                HaveAnonymousActionsDialogFragment dialogFragment = new HaveAnonymousActionsDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "HAVE_ANONYMOUS_ACTIONS_DIALOG");
            }
        });
    }

    private void setupObserves() {
        viewModel.created.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    binding.loader.setVisibility(View.GONE);
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_createAccount_to_chooseFragment);

                } else {
                    binding.buttonSignUp.setEnabled(true);
                    binding.loader.setVisibility(View.GONE);
                    Snackbar.make(requireView(), getString(R.string.account_with_this_email_is_already_exist), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        viewModel.verified.observe(getViewLifecycleOwner(), verified -> {
            if (verified) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_createAccount_to_chooseFragment);
            } else {
                binding.loader.setVisibility(View.GONE);
            }
        });
    }

    private void createVerificationDialog(String email) {
        VerificationDialogFragment dialogFragment = new VerificationDialogFragment();
        if (email != null && password != null) {
            viewModel.vEmail = email;
            viewModel.vPassword = password;
        }
        dialogFragment.setup(viewModel.vEmail, viewModel.vPassword);

        dialogFragment.show(requireActivity().getSupportFragmentManager(), "VERIFICATON_DIALOG");
        DialogDismissedListener listener = bundle -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_createAccount_to_chooseFragment);
        };
        dialogFragment.onDismissListener(listener);
    }

    private void initImagePicker() {
        ImagePicker.with(this)
                .cropSquare()
                .compress(512)
                .maxResultSize(512, 512)
                .createIntent(intent -> {
                    activityResultLauncher.launch(intent);
                    return null;
                });
    }

    private void setActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri = data.getData();
                            Glide.with(CreateAccountFragment.this).load(imageUri).apply(RequestOptions.circleCropTransform())
                                    .into(binding.addPhotoLayout);
                        }
                    }
                });
    }

    private void initEditText() {

        binding.editLayoutEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (!text.toString().isEmpty()) {
                    viewModel.removeEmailError();
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
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (!text.toString().isEmpty()) {
                    viewModel.removePasswordError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.editLayoutName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (!text.toString().isEmpty()) {
                    viewModel.removeNameError();
                }
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