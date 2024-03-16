package com.example.myapplication.presentation.profile.loggedProfile.main;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.STATE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileLoggedBinding;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.CompanyUserFragment;

/*
 * @author j.gladkikh
 */
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
        type = getArguments().getString(STATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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



        if (!viewModel.checkAuth() || !viewModel.isNull()) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_profileLoggedFragment_to_navigation_profile);
        }

    }

    private void setView() {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (type) {
            case BASIC:
                setBasicLayout(fragmentManager);
                break;

            case COMPANY:
                String companyId = getArguments().getString(COMPANY_ID);
                Bundle bundle = new Bundle();
                id = companyId;
                bundle.putString(COMPANY_ID, companyId);

                fragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.logged_container, CompanyUserFragment.class, bundle)
                        .commit();
                break;
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