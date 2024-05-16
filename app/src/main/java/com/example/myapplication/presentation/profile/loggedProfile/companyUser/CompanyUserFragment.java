package com.example.myapplication.presentation.profile.loggedProfile.companyUser;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.model.CompanyUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.settingsCompany.SettingsCompanyActivity;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.state.CompanyUserState;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemModel;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class CompanyUserFragment extends Fragment {

    private CompanyUserViewModel viewModel;
    private FragmentCompanyUserBinding binding;
    private String companyId, type;
    private boolean isExist = true;
    private int editTextId = 0;
    private ActivityResultLauncher<Intent> launcher;
    private final List<DelegateItem> delegates = new ArrayList<>();
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        companyId = sharedPreferences.getString(COMPANY_ID, null);
        type = sharedPreferences.getString(APP_STATE, null);
        initLauncher();
    }

    private void initLauncher() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString(APP_STATE, BASIC).apply();
                        sharedPreferences.edit().putString(COMPANY_ID, null).apply();

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager
                                .beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.logged_container, BasicUserFragment.class, null)
                                .commit();
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CompanyUserViewModel.class);

        switch (type) {
            case COMPANY:
                editTextId = R.string.edit_company;
                viewModel.getCompany(companyId);
                break;
            case RESTAURANT:
                editTextId = R.string.edit_restaurant;
                viewModel.getRestaurant(companyId);
                break;
        }

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

    private void setAdapter() {
        mainAdapter.addDelegate(new MainItemDelegate());
        mainAdapter.addDelegate(new ServiceItemDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void initSettings() {
        binding.buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SettingsCompanyActivity.class);
            intent.putExtra(COMPANY_ID, companyId);
            launcher.launch(intent);
        });
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof CompanyUserState.Success) {
                CompanyUserModel model = ((CompanyUserState.Success) state).data;
                initRecycler(model);

            } else if (state instanceof CompanyUserState.Loading) {
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof CompanyUserState.Error) {
                setErrorLayout();
            }
        });

        viewModel.isExist.observe(getViewLifecycleOwner(), aBoolean -> {
            isExist = aBoolean;
            if (!aBoolean) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                sharedPreferences.edit().putString(APP_STATE, BASIC).apply();
                sharedPreferences.edit().putString(COMPANY_ID, null).apply();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.logged_container, BasicUserFragment.class, null)
                        .commit();
            }
        });

    }

    private void setErrorLayout() {
        binding.progressBar.setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            switch (type){
                case RESTAURANT:
                    viewModel.getRestaurant(companyId);
                    break;
                case COMPANY:
                    viewModel.getCompany(companyId);
                    break;
            }
        });
    }

    private void initRecycler(CompanyUserModel model) {
        delegates.add(new MainItemDelegateItem(new MainItemModel(
                0, model.getUri(), model.getName(), model.getEmail(), type, companyId))
        );
        delegates.add(new ServiceItemDelegateItem(new ServiceItemModel(1, R.drawable.ic_edit, editTextId, () -> {
            ((MainActivity) requireActivity()).openEditCompanyActivity(companyId);
        })));

        delegates.add(new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_person_filled_24, R.string.go_to_my_profile, () -> {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(APP_STATE, BASIC).apply();
            sharedPreferences.edit().putString(COMPANY_ID, null).apply();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.logged_container, BasicUserFragment.class, null)
                    .commit();
        })));

        mainAdapter.submitList(delegates);
        binding.progressBar.setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.GONE);
    }


}