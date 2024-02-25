package com.example.myapplication.presentation.profile.editProfile;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.Utils.FEMALE_KEY;
import static com.example.myapplication.presentation.utils.Utils.MALE_KEY;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.databinding.FragmentEditProfileBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

///*
// * @author j.gladkikh
// */
public class EditProfileFragment extends Fragment {
//
    private EditProfileViewModel viewModel;
    private FragmentEditProfileBinding binding;
    private String newUserName, newPhoneNumber, gender, email;
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
//
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setupObserves();

        viewModel.getProfileImage();
        viewModel.retrieveUserData();

        initChangePhotoButton();
        initSaveDataButton();
        initBackButton();

    }

    private void initBackButton() {
        binding.imageButtonBackEdit.setOnClickListener(v ->
                requireActivity().finish());
    }

    private void imagePicker(ActivityResultLauncher<Intent> activityResultLauncher, Fragment fragment) {
        ImagePicker.with(fragment)
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
                                    .into(binding.profilePhotoEditFragment);
                        }
                    }
                });
    }

    private void initChangePhotoButton(){
        binding.actionButtonChangePhoto.setOnClickListener(
                view -> imagePicker(activityResultLauncher, this));
    }

    private void initSaveDataButton(){
        binding.saveButton.setOnClickListener(v -> {

            newUserName = binding.userNameEdit.getText().toString();
            newPhoneNumber = binding.userPhoneNumberEdit.getText().toString();
            email = binding.userEmailEdit.getText().toString();

            if (binding.radioButtonFemaleEditFragment.isChecked()){
                gender = FEMALE_KEY;
            } else {
                gender = MALE_KEY;
            }

            viewModel.showProgress(true);
            viewModel.saveData(newUserName, newPhoneNumber, gender, imageUri, this);
        });
    }
    private void setupObserves(){

        viewModel.image.observe(getViewLifecycleOwner(), uriTask -> {
            uriTask.addOnSuccessListener(uri -> {
                Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(binding.profilePhotoEditFragment);
            });
        });

        viewModel.femaleMode.observe(getViewLifecycleOwner(), setChecked -> {
            if (setChecked){
                binding.radioButtonFemaleEditFragment.setChecked(true);
            }
        });

        viewModel.maleMode.observe(getViewLifecycleOwner(), setChecked -> {
            if (setChecked){
                binding.radioButtonMaleEditFragment.setChecked(true);
            }
        });

        viewModel.userName.observe(getViewLifecycleOwner(), string -> {
            binding.userNameEdit.setText(string);
        });

        viewModel.userEmail.observe(getViewLifecycleOwner(), string -> {
            binding.userEmailEdit.setText(string);
        });

        viewModel.phoneNumber.observe(getViewLifecycleOwner(), string -> {
            binding.userPhoneNumberEdit.setText(string);
        });
    }
}
