package com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.EMAIL;
import static com.example.myapplication.presentation.utils.constants.Utils.PASSWORD;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEditProfileBinding;
import com.example.myapplication.presentation.dialogFragments.changeEmail.ChangeEmailDialogFragment;
import com.example.myapplication.presentation.dialogFragments.emailUpdateSuccessful.EmailUpdateSuccessfulDialogFragment;
import com.example.myapplication.presentation.dialogFragments.verifyBeforeUpdateDialogFragment.VerifyBeforeUpdateDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.model.EditBasicUserState;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.model.EditUserModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class EditProfileFragment extends Fragment {
    private EditProfileViewModel viewModel;
    private FragmentEditProfileBinding binding;
    private String name, phone, email, gender, birthday;
    private Uri imageUri;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        setActivityResultLauncher();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initPhoneEditText();
        setupObserves();
        viewModel.retrieveUserData();
        initBirthdayButton();
        initChangePhotoButton();
        initSaveDataButton();
        initBackButton();
        initChangeEmail();
    }

    private void initPhoneEditText() {
        setupPhoneEditText();
    }

    private void setupPhoneEditText() {
        binding.editLayoutPhone.setText("+7");
        binding.editLayoutPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onPhoneTextChanged(s, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onPhoneTextChanged(CharSequence text, int dir) {
        int size = text.length();
        int last = size - 1;

        Log.d("Text change test", "Size:" + size + "Dir:" + dir + "Text:" + text);

        StringBuilder t = new StringBuilder(binding.editLayoutPhone.getText());

        if (size <= 2 && dir == 0) {
            setTextAndSelection("+7", 2);
        }

        if (size == 3 && dir == 1) {
            setTextAndSelection(t.substring(0, last) + " (" + text.charAt(last), 5);
        }

        if (size == 7 && dir == 1) {
            setTextAndSelection(t + ")", 8);
        }

        if ((size == 8 && dir == 1)  && t.charAt(last - 1) != '-') {
            setTextAndSelection(t.substring(0, 7) + ") " + t.charAt(last), 10);
        }

        if (size == 9 && dir == 1 && t.charAt(last - 1) != ' ') {
            setTextAndSelection(t.substring(0, 8) + " " + t.charAt(last), 10);
        }

        checkZeroOneDirectional(size, 13, '-', last, dir, t.toString());

        if ((size == 16 && dir == 0) || (size == 13 && dir == 0) || (size == 8 && dir == 0)){
            setTextAndSelection(text.toString().substring(0, text.length() - 1), text.length() - 1);
        }

        if ((size == 4 && dir == 0)){
            setTextAndSelection(text.toString().substring(0, text.length() - 2), text.length() - 2);
        }

        if (size > 18 && dir == 1) {
            setTextAndSelection(t.substring(0, 18), 18);
        }
    }

    private void setTextAndSelection(String text, int selection) {
        binding.editLayoutPhone.setText(text);
        binding.editLayoutPhone.setSelection(selection);
    }

    private void checkZeroOneDirectional(int size, int maxSize, char symbol, int lastIndex, int direction, String text) {
        if ((size == maxSize && direction == 1 && text.charAt(lastIndex - 1) != symbol)   ) {
            setTextAndSelection(text.substring(0, maxSize - 1) + symbol + text.substring(maxSize - 1), maxSize + 1);
        }

        if ((size == 15 && direction == 1)){
            setTextAndSelection( text + symbol, maxSize + 3);
        }

        if (size == 16 && direction == 1){
            setTextAndSelection(text.substring(0, text.length() - 1) + symbol + text.substring(text.length() - 1), maxSize + 4);
        }
    }

    private void initChangeEmail() {
        binding.buttonChangeEmail.setOnClickListener(v -> {
            ChangeEmailDialogFragment dialogFragment = new ChangeEmailDialogFragment();
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "CHANGE_EMAIL_DIALOG");
            DialogDismissedListener listener = bundle -> {

                email = bundle.getString(EMAIL);
                String password = bundle.getString(PASSWORD);
                viewModel.verifyBeforeUpdate(email, password);

            };
            dialogFragment.onDismissListener(listener);
        });
    }

    private void initBirthdayButton() {
        binding.editLayoutData.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext());
            datePickerDialog.show();
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                birthday = dayOfMonth + "." + (month + 1) + "." + year;
                binding.editLayoutData.setText(birthday);
                datePickerDialog.dismiss();
            });
        });
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v ->
                requireActivity().finish());
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
                            Glide.with(EditProfileFragment.this).load(imageUri).apply(RequestOptions.circleCropTransform())
                                    .into(binding.profilePhoto);
                        }
                    }
                });
    }

    private void initChangePhotoButton() {
        binding.profilePhoto.setOnClickListener(
                view -> initImagePicker());
    }

    private void initSaveDataButton() {
        binding.buttonSave.setOnClickListener(v -> {

            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonSave.setEnabled(false);

            name = binding.editLayoutName.getText().toString();
            phone = binding.editLayoutPhone.getText().toString();
            email = binding.editLayoutEmail.getText().toString();
            gender = binding.editLayoutGender.getText().toString();
            birthday = binding.editLayoutData.getText().toString();
            if (name != null && (phone.length() == 18 || phone.length() == 2)) {
                viewModel.saveData(name, phone, gender, birthday, imageUri, this);
            } else if (name == null) {
                binding.loader.setVisibility(View.GONE);
                binding.buttonSave.setEnabled(true);
                Snackbar.make(requireView(), getString(R.string.name_cannot_be_null), Snackbar.LENGTH_LONG).show();
            } else {
                binding.loader.setVisibility(View.GONE);
                binding.buttonSave.setEnabled(true);
                Snackbar.make(requireView(), R.string.phone_must_be_full , Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof EditBasicUserState.Success) {
                EditUserModel editUserModel = ((EditBasicUserState.Success) state).data;

                Uri uri = editUserModel.getUri();

                binding.editLayoutName.setText(editUserModel.getName());
                binding.editLayoutEmail.setText(editUserModel.getEmail());
                binding.editLayoutGender.setText(editUserModel.getGender());
                binding.editLayoutGender.setSimpleItems(R.array.genders);
                binding.editLayoutData.setText(editUserModel.getBirthday());
                binding.editLayoutPhone.setText(editUserModel.getPhoneNumber());

                if (!(uri == Uri.EMPTY)) {

                    Glide.with(this)
                            .load(uri)
                            .addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                                    binding.progressLayout.getRoot().setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .apply(RequestOptions.circleCropTransform())
                            .into(binding.profilePhoto);
                } else {
                    binding.progressLayout.getRoot().setVisibility(View.GONE);
                }


            } else if (state instanceof EditBasicUserState.Loading) {
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof EditBasicUserState.Error) {

            }
        });

        viewModel.openSuccessDialog.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                EmailUpdateSuccessfulDialogFragment dialogFragment = new EmailUpdateSuccessfulDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "SUCCESS_DIALOG");
            }
        });

        viewModel.openVerifyDialog.observe(getViewLifecycleOwner(), password -> {
            if (password != null) {
                VerifyBeforeUpdateDialogFragment dialogFragment = new VerifyBeforeUpdateDialogFragment(email, password);
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "VERIFY_BEFORE_UPDATE_DIALOG");
                DialogDismissedListener dismissedListener = object -> {
                    viewModel.updateEmailField(email);
                };
                dialogFragment.onDismissListener(dismissedListener);
            }
        });

    }
}
