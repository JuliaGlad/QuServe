package com.example.myapplication.presentation.profile.loggedProfile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.databinding.FragmentProfileLoggedBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.R;

/*
 * @author j.gladkikh
 */
public class ProfileLoggedFragment extends Fragment {

    FragmentProfileLoggedBinding binding;
    private ProfileLoggedViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileLoggedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileLoggedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setupObserves();

        viewModel.getProfileImage();
        viewModel.retrieveUserNameData();

        initEditNavigationButton();
        initHistoryNavigationButton();
        initSettingsNavigationButton();
    }

    private void initEditNavigationButton() {
        binding.constraintLayoutEdit.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).openEditActivity();

        });
    }

    private void initHistoryNavigationButton() {
        binding.constraintLayoutHistory.setOnClickListener(v ->
                ((MainActivity) requireActivity()).openHistoryActivity());
    }

    private void initSettingsNavigationButton() {
        binding.constraintLayoutSettings.setOnClickListener(view -> NavHostFragment.findNavController(ProfileLoggedFragment.this)
                .navigate(R.id.action_profileLoggedFragment_to_settingsFragment));
    }

    private void setupObserves(){
        viewModel.image.observe(getViewLifecycleOwner(), uriTask -> {
            uriTask.addOnSuccessListener(uri -> Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(binding.profilePhoto));
        });

        viewModel.userName.observe(getViewLifecycleOwner(), string -> {
            binding.userProfileName.setText(string);
        });

        viewModel.userEmail.observe(getViewLifecycleOwner(), string -> {
            binding.email.setText(string);
        });
    }
}