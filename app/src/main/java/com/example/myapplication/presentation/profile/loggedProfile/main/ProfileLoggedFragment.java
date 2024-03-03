package com.example.myapplication.presentation.profile.loggedProfile.main;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.databinding.FragmentProfileLoggedBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.CompanyUserFragment;

import java.sql.SQLData;

/*
 * @author j.gladkikh
 */
public class ProfileLoggedFragment extends Fragment {

    FragmentProfileLoggedBinding binding;
    private ProfileLoggedViewModel viewModel;
    private String page;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getString(PAGE_KEY);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (page) {
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
}