package com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile;

import static android.app.Activity.RESULT_OK;

import static com.google.common.base.StandardSystemProperty.USER_NAME;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEditProfileBinding;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.basicUserStateAndModel.BasicUserState;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.model.EditBasicUserState;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.model.EditUserModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

///*
// * @author j.gladkikh
// */
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setupObserves();
        viewModel.retrieveUserData();

        initBirthdayButton();
        initChangePhotoButton();
        initSaveDataButton();
        initBackButton();

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

            name = binding.editLayoutName.getText().toString();
            phone = binding.editLayoutPhone.getText().toString();
            email = binding.editLayoutEmail.getText().toString();
            gender = binding.editLayoutGender.getText().toString();
            birthday = binding.editLayoutData.getText().toString();
            viewModel.saveData(name, phone, gender, birthday, imageUri, this);
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
                                    binding.progressLayout.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .apply(RequestOptions.circleCropTransform())
                            .into(binding.profilePhoto);
                } else {
                    binding.progressLayout.setVisibility(View.GONE);
                }


            } else if (state instanceof EditBasicUserState.Loading) {
                binding.progressLayout.setVisibility(View.VISIBLE);

            } else if (state instanceof EditBasicUserState.Error) {

            }
        });

    }
}
