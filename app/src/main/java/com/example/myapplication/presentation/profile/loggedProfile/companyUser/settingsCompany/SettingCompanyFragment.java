package com.example.myapplication.presentation.profile.loggedProfile.companyUser.settingsCompany;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

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
import com.example.myapplication.databinding.FragmentSettingCompanyBinding;
import com.example.myapplication.presentation.dialogFragments.aboutUs.AboutUsDialogFragment;
import com.example.myapplication.presentation.dialogFragments.deleteCompany.DeleteCompanyDialogFragment;
import com.example.myapplication.presentation.dialogFragments.help.HelpDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.BasicUserFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.Arguments;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate.SettingsUserItemDelegate;

import myapplication.android.ui.listeners.DialogDismissedListener;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class SettingCompanyFragment extends Fragment {

    private SettingCompanyViewModel viewModel;
    private FragmentSettingCompanyBinding binding;
    private String companyId;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyId = getActivity().getIntent().getStringExtra(COMPANY_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(SettingCompanyViewModel.class);
        binding = FragmentSettingCompanyBinding.inflate(inflater, container, false);

        viewModel.getCompany(companyId);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setupObserves();
    }

    private void setAdapter(){
        mainAdapter.addDelegate(new SettingsUserItemDelegate());
        mainAdapter.addDelegate(new ServiceItemDelegate());
        mainAdapter.addDelegate(new ServiceRedItemDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves(){
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);

        viewModel.openHelpDialog.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                HelpDialogFragment dialogFragment = new HelpDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "HELP_DIALOG");
            }
        });

        viewModel.openAboutUsDialog.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                AboutUsDialogFragment dialogFragment = new AboutUsDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "ABOUT_US_DIALOG");
            }
        });

        viewModel.openDeleteDialog.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                DeleteCompanyDialogFragment dialogFragment = new DeleteCompanyDialogFragment(companyId);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "DELETE_COMPANY_DIALOG");
                DialogDismissedListener listener = bundle -> {
                    Arguments.isDeleted = true;
                    requireActivity().finish();
                };
                dialogFragment.onDismissListener(listener);
            }
        });
    }
}