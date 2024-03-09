package com.example.myapplication.presentation.profile.loggedProfile.main;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.STATE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Dao;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileLoggedBinding;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.CompanyUserFragment;

/*
 * @author j.gladkikh
 */
public class ProfileLoggedFragment extends Fragment {
    FragmentProfileLoggedBinding binding;
    private ProfileLoggedViewModel viewModel;
    private String type;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getArguments().getString(STATE);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (type) {
            case BASIC:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.logged_container, BasicUserFragment.class, null)
                        .commit();
                break;
            case COMPANY:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.logged_container, CompanyUserFragment.class, null)
                        .commit();
                break;
        }


        viewModel = new ViewModelProvider(this).get(ProfileLoggedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileLoggedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!DI.checkAuthentificationUseCase.invoke() || service.auth.getCurrentUser() == null){
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_profileLoggedFragment_to_navigation_profile);
        }
    }
}