package com.example.myapplication.presentation.profile.loggedProfile.basicUser;

import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.databinding.FragmentBasicUserBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.mainItem.MainItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem.ServiceItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.BasicSettingsFragment;

import myapplication.android.ui.recycler.delegate.MainAdapter;

public class BasicUserFragment extends Fragment {

    private BasicUserViewModel viewModel;
    private FragmentBasicUserBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private boolean companyExist = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onCreate BasicUserFragment", "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(BasicUserViewModel.class);

        viewModel.checkCompanyExist();
        viewModel.retrieveUserNameData(this);
        binding = FragmentBasicUserBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setAdapter();
        initSettingButton();
    }

    private void initSettingButton() {
        binding.buttonSettings.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openSettingsActivity();
        });
    }

    private void setAdapter() {

        mainAdapter.addDelegate(new MainItemDelegate());
        mainAdapter.addDelegate(new ServiceItemDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }


    private void setupObserves() {
        viewModel.item.observe(getViewLifecycleOwner(), mainAdapter::submitList);
    }
}