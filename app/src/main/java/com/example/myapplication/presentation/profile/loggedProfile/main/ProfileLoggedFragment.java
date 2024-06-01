package com.example.myapplication.presentation.profile.loggedProfile.main;

import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.BASIC;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileLoggedBinding;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.CompanyUserFragment;
import com.example.myapplication.presentation.profile.profileLogin.ProfileLoginFragment;

public class ProfileLoggedFragment extends Fragment {
    FragmentProfileLoggedBinding binding;
    ProfileLoggedFragmentViewModel viewModel;
    private String type, id;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProfileLoggedFragmentViewModel.class);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(APP_STATE, ANONYMOUS);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileLoggedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!viewModel.checkAuth() || viewModel.isNull()) {
            type = ANONYMOUS;
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            fragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.logged_container, ProfileLoginFragment.class, null)
                    .commit();
        }

    }

    private void setView() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (type != null) {
            switch (type) {
                case BASIC:
                    setBasicLayout(fragmentManager);
                    break;

                case COMPANY:
                case RESTAURANT:
                    fragmentManager
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.logged_container, CompanyUserFragment.class, null)
                            .commit();
                    break;

                case ANONYMOUS:
                    fragmentManager
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.logged_container, ProfileLoginFragment.class, null)
                            .commit();
            }
        }
    }

    private void setBasicLayout(FragmentManager fragmentManager) {
        fragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.logged_container, BasicUserFragment.class, null)
                .commit();
    }
}