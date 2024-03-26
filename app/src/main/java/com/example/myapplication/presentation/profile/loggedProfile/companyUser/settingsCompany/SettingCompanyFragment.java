package com.example.myapplication.presentation.profile.loggedProfile.companyUser.settingsCompany;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSettingCompanyBinding;
import com.example.myapplication.presentation.dialogFragments.aboutUs.AboutUsDialogFragment;
import com.example.myapplication.presentation.dialogFragments.deleteCompany.DeleteCompanyDialogFragment;
import com.example.myapplication.presentation.dialogFragments.help.HelpDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.Arguments;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.model.CompanyUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.state.CompanyUserState;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate.SettingsUserItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate.SettingsUserItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate.SettingsUserItemModel;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.listeners.DialogDismissedListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class SettingCompanyFragment extends Fragment {

    private SettingCompanyViewModel viewModel;
    private FragmentSettingCompanyBinding binding;
    private String companyId;
    private final List<DelegateItem> delegates = new ArrayList<>();
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

    private void initRecycler(String name, String email, Uri uri){
        delegates.add(new SettingsUserItemDelegateItem(new SettingsUserItemModel(0, name, email, uri)));
        delegates.add( new ServiceItemDelegateItem(new ServiceItemModel(1, R.drawable.ic_shield_person, R.string.change_owner, () -> {

        })));
        delegates.add( new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_help, R.string.help, () -> {

            HelpDialogFragment dialogFragment = new HelpDialogFragment();
            dialogFragment.show(getActivity().getSupportFragmentManager(), "HELP_DIALOG");
        })));
        delegates.add( new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_group, R.string.about_us, () -> {

            AboutUsDialogFragment dialogFragment = new AboutUsDialogFragment();
            dialogFragment.show(getActivity().getSupportFragmentManager(), "ABOUT_US_DIALOG");
        })));
        delegates.add(new ServiceRedItemDelegateItem(new ServiceRedItemModel(3, R.drawable.ic_delete, R.string.delete_company, () -> {

            DeleteCompanyDialogFragment dialogFragment = new DeleteCompanyDialogFragment(companyId);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "DELETE_COMPANY_DIALOG");
            DialogDismissedListener listener = bundle -> {
                Arguments.isDeleted = true;
                requireActivity().finish();
            };
            dialogFragment.onDismissListener(listener);
        })));

        mainAdapter.submitList(delegates);
        binding.progressBar.setVisibility(View.GONE);
    }

    private void setupObserves(){
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof CompanyUserState.Success){
                CompanyUserModel model = ((CompanyUserState.Success)state).data;
                if (delegates.size() == 0){
                    initRecycler(model.getName(), model.getEmail(), model.getUri());
                }
            } else if (state instanceof CompanyUserState.Loading){
                binding.progressBar.setVisibility(View.VISIBLE);
            } else if (state instanceof CompanyUserState.Error){

            }
        });

    }
}