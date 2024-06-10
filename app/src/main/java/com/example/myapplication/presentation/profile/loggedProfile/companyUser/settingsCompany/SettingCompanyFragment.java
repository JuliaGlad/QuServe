package com.example.myapplication.presentation.profile.loggedProfile.companyUser.settingsCompany;

import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.myapplication.presentation.dialogFragments.deleteRestaurant.DeleteRestaurantDialogFragment;
import com.example.myapplication.presentation.dialogFragments.help.HelpDialogFragment;
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
    private String companyId, state;
    private int deleteTextId = 0;
    private final List<DelegateItem> delegates = new ArrayList<>();
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingCompanyViewModel.class);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        state = sharedPreferences.getString(APP_STATE, ANONYMOUS);
        companyId = sharedPreferences.getString(COMPANY_ID, null);

        switch (state){
            case COMPANY:
                deleteTextId = R.string.delete_company;
                viewModel.getCompany(companyId);
                break;
            case RESTAURANT:
                deleteTextId = R.string.delete_restaurant;
                viewModel.getRestaurant(companyId);
                break;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSettingCompanyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setupObserves();
        initBackButton();
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setAdapter(){
        mainAdapter.addDelegate(new SettingsUserItemDelegate());
        mainAdapter.addDelegate(new ServiceItemDelegate());
        mainAdapter.addDelegate(new ServiceRedItemDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void initRecycler(String name, String email, Uri uri){
        delegates.add(new SettingsUserItemDelegateItem(new SettingsUserItemModel(0, name, email, uri)));
        delegates.add( new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_help, R.string.help, () -> {

            HelpDialogFragment dialogFragment = new HelpDialogFragment();
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "HELP_DIALOG");
        })));
        delegates.add( new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_group, R.string.about_us, () -> {

            AboutUsDialogFragment dialogFragment = new AboutUsDialogFragment();
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "ABOUT_US_DIALOG");
        })));
        delegates.add(new ServiceRedItemDelegateItem(new ServiceRedItemModel(3, R.drawable.ic_delete, deleteTextId, this::showDeleteDialog)));

        mainAdapter.submitList(delegates);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.GONE);
    }

    private void showDeleteDialog() {
        switch (state){
            case COMPANY:
                DeleteCompanyDialogFragment dialogFragment = new DeleteCompanyDialogFragment(companyId);
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "DELETE_COMPANY_DIALOG");
                DialogDismissedListener listener = bundle -> {
                    Intent intent = new Intent();
                    requireActivity().setResult(Activity.RESULT_OK, intent);
                    requireActivity().finish();
                };
                dialogFragment.onDismissListener(listener);
                break;
            case RESTAURANT:
                DeleteRestaurantDialogFragment dialogFragmentRestaurant = new DeleteRestaurantDialogFragment(companyId);
                dialogFragmentRestaurant.show(requireActivity().getSupportFragmentManager(), "DELETE_RESTAURANT_DIALOG");
                dialogFragmentRestaurant.onDismissListener(bundle -> {
                    Intent intent = new Intent();
                    requireActivity().setResult(Activity.RESULT_OK, intent);
                    requireActivity().finish();
                });
                break;
        }
    }

    private void setupObserves(){
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof CompanyUserState.Success){
                CompanyUserModel model = ((CompanyUserState.Success)state).data;
                if (delegates.isEmpty()){
                    initRecycler(model.getName(), model.getEmail(), model.getUri());
                }
            } else if (state instanceof CompanyUserState.Loading){
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof CompanyUserState.Error){
                setError();
            }
        });

    }

    private void setError() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            switch (state){
                case COMPANY:
                    viewModel.getCompany(companyId);
                    break;
                case RESTAURANT:
                    viewModel.getRestaurant(companyId);
                    break;
            }
        });
    }
}