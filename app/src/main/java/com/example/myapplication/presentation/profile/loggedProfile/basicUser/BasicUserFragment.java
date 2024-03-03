package com.example.myapplication.presentation.profile.loggedProfile.basicUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBasicUserBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.SettingsFragment;
import com.example.myapplication.presentation.profile.loggedProfile.chooseCompany.ChooseCompanyFragment;

public class BasicUserFragment extends Fragment {

    private BasicUserViewModel viewModel;
    private FragmentBasicUserBinding binding;
    private boolean companyExist = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(BasicUserViewModel.class);

        viewModel.checkCompanyExist();

        binding = FragmentBasicUserBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        viewModel.getProfileImage();
        viewModel.retrieveUserNameData();

        initEditNavigationButton();
        initCompanyButton();
        initHistoryNavigationButton();
        initSettingsNavigationButton();

    }

    private void initCompanyButton() {
        binding.constraintLayoutToCompany.setOnClickListener(v -> {
            if (companyExist) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.logged_container, ChooseCompanyFragment.class, null)
                        .commit();
            }
        });
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
        binding.constraintLayoutSettings.setOnClickListener(view -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .addToBackStack(".presentation.profile.loggedProfile.basicUser.BasicUserFragment")
                    .replace(R.id.logged_container, SettingsFragment.class, null)
                    .commit();
        });
    }

    private void setupObserves() {
        viewModel.imageUpdated.observe(getViewLifecycleOwner(), uriTask -> {
            uriTask.addOnSuccessListener(uri -> {
                Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(binding.profilePhoto);
            });
        });

        viewModel.image.observe(getViewLifecycleOwner(), uriTask -> {
            uriTask.addOnSuccessListener(uri -> {
                Glide.with(this)
                        .load(uri)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.profilePhoto);
            });
        });

        viewModel.userName.observe(getViewLifecycleOwner(), string -> {
            binding.userProfileName.setText(string);
        });

        viewModel.userEmail.observe(getViewLifecycleOwner(), string -> {
            binding.email.setText(string);
        });

        viewModel.companyExist.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                companyExist = true;
            }
        });
    }
}