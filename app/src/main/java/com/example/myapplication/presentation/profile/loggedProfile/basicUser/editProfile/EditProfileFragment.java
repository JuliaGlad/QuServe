package com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEditProfileBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

///*
// * @author j.gladkikh
// */
public class EditProfileFragment extends Fragment {
//
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

        viewModel.getProfileImage();
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

    private void initChangePhotoButton(){
        binding.profilePhoto.setOnClickListener(
                view -> initImagePicker());
    }

    private void initSaveDataButton(){
        binding.buttonSave.setOnClickListener(v -> {

            name = binding.editLayoutName.getText().toString();
            phone = binding.editLayoutPhone.getText().toString();
            email = binding.editLayoutEmail.getText().toString();
            gender = binding.editLayoutGender.getText().toString();
            viewModel.saveData(name, phone, gender, birthday, imageUri, this);
        });
    }
    private void setupObserves(){

        viewModel.image.observe(getViewLifecycleOwner(), uri -> {
            if (!(uri == Uri.EMPTY)) {
                Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(binding.profilePhoto);
            }
        });

        viewModel.userName.observe(getViewLifecycleOwner(), string -> {
            binding.editLayoutName.setText(string);
        });

        viewModel.userEmail.observe(getViewLifecycleOwner(), string -> {
            binding.editLayoutEmail.setText(string);
        });

        viewModel.phoneNumber.observe(getViewLifecycleOwner(), string -> {
            binding.editLayoutPhone.setText(string);
        });

        viewModel.birthday.observe(getViewLifecycleOwner(), string -> {
            binding.editLayoutData.setText(string);
        });

        viewModel.gender.observe(getViewLifecycleOwner(), string -> {
            binding.editLayoutGender.setText(string);
            binding.editLayoutGender.setSimpleItems(R.array.genders);
        });
    }
}
