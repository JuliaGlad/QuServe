package com.example.myapplication.presentation.profile.loggedProfile.companyUser;

import static com.example.myapplication.presentation.utils.Utils.COMPANY;
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

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCompanyUserBinding;
import com.example.myapplication.domain.model.company.CompanyNameAndEmailModel;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.model.CompanyUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.state.CompanyUserState;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem.MainItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class CompanyUserFragment extends Fragment {

    private CompanyUserViewModel viewModel;
    private FragmentCompanyUserBinding binding;
    private String companyId;
    private boolean isExist = true;
    private final List<DelegateItem> delegates = new ArrayList<>();
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

        if (Arguments.isDeleted) {
            Arguments.isDeleted = false;
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
            ((MainActivity) requireActivity()).openCompanySettingsActivity(companyId);
        });
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof CompanyUserState.Success){
                CompanyUserModel model = ((CompanyUserState.Success)state).data;
                delegates.add(new MainItemDelegateItem(new MainItemModel(
                        0, model.getUri(), model.getName(), model.getEmail(), COMPANY, companyId))
                );
                delegates.add( new ServiceItemDelegateItem(new ServiceItemModel(1, R.drawable.ic_edit, R.string.edit_company, () -> {
                    ((MainActivity) requireActivity()).openEditCompanyActivity(companyId);
                })));

                delegates.add( new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_group, R.string.employees, () -> {
                    ((MainActivity) requireActivity()).openEmployeesActivity(companyId);
                })));

                delegates.add(new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_person_filled_24, R.string.go_to_my_profile, () -> {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.logged_container, BasicUserFragment.class, null)
                            .commit();
                })));

                mainAdapter.submitList(delegates);
                binding.progressBar.setVisibility(View.GONE);

            } else if (state instanceof CompanyUserState.Loading){
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof CompanyUserState.Error) {

            }
        });

        viewModel.isExist.observe(getViewLifecycleOwner(), aBoolean -> {
            isExist = aBoolean;
            if (!aBoolean) {
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