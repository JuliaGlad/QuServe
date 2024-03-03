package com.example.myapplication.presentation.profile.loggedProfile.chooseCompany;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentChooseCompanyBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.chooseCompany.companyDelegate.CompanyDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.CompanyUserFragment;

import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;

public class ChooseCompanyFragment extends Fragment {

    private ChooseCompanyViewModel viewModel;
    private FragmentChooseCompanyBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChooseCompanyViewModel.class);
        binding = FragmentChooseCompanyBinding.inflate(inflater, container, false);
        viewModel.getCompaniesList();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setAdapter();
        initBackButton();
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.logged_container, BasicUserFragment.class, null)
                    .commit();
        });
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new FloatingActionButtonDelegate());
        mainAdapter.addDelegate(new CompanyDelegate());
        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves(){
        viewModel.item.observe(getViewLifecycleOwner(), mainAdapter::submitList);

        viewModel.navigate.observe(getViewLifecycleOwner(), id -> {

            if (id != null) {
                Bundle bundle = new Bundle();
                bundle.putString("COMPANY_ID", id);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.logged_container, CompanyUserFragment.class, bundle)
                        .commit();
            }
        });

        viewModel.openCreateActivity.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                ((MainActivity)requireActivity()).openCreateCompanyQueueActivity();
            }
        });
    }
}