package com.example.myapplication.presentation.profile.loggedProfile.companyUser;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCompanyUserBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegate;

import myapplication.android.ui.recycler.delegate.MainAdapter;

public class CompanyUserFragment extends Fragment {

    private CompanyUserViewModel viewModel;
    private FragmentCompanyUserBinding binding;
    private String companyId ;
    private boolean isExist = true;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        companyId = getArguments().getString(COMPANY_ID);

        viewModel = new ViewModelProvider(this).get(CompanyUserViewModel.class);
        viewModel.getCompany(companyId);
        binding = FragmentCompanyUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setAdapter();
        initSettings();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.setLiveDataToDefault();
        if (Arguments.isDeleted){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.logged_container, BasicUserFragment.class, null)
                    .commit();
        }
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new MainItemDelegate());
        mainAdapter.addDelegate(new ServiceItemDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void initSettings() {
        binding.buttonSettings.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openCompanySettingsActivity(companyId);
        });
    }

    private void setupObserves() {

        viewModel.isExist.observe(getViewLifecycleOwner(), aBoolean -> {
            isExist = aBoolean;
            if (!aBoolean){
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.logged_container, BasicUserFragment.class, null)
                        .commit();
            }
        });

        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);

        viewModel.openEditActivity.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                ((MainActivity)requireActivity()).openEditCompanyActivity(companyId);
            }
        });

        viewModel.openEmployeesActivity.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                ((MainActivity)requireActivity()).openEmployeesActivity(companyId);
            }
        });

        viewModel.goToProfile.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.logged_container, BasicUserFragment.class, null)
                        .commit();
            }
        });

    }


}