package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBasicSettingsBinding;
import com.example.myapplication.presentation.dialogFragments.aboutUs.AboutUsDialogFragment;
import com.example.myapplication.presentation.dialogFragments.help.HelpDialogFragment;
import com.example.myapplication.presentation.dialogFragments.logout.LogoutDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem.ServiceItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceRedItem.ServiceRedItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.userItemDelegate.SettingsUserItemDelegate;

import myapplication.android.ui.listeners.DialogDismissedListener;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class BasicSettingsFragment extends Fragment {

    private BasicSettingsViewModel viewModel;
    private FragmentBasicSettingsBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();

    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("OnCreate BasicSettingsFragment", "onCreate");

        viewModel = new ViewModelProvider(this).get(BasicSettingsViewModel.class);

        settings = requireActivity().getSharedPreferences("THEME", MODE_PRIVATE);
        prefEditor = settings.edit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasicSettingsBinding.inflate(inflater, container, false);
        viewModel.retrieveUserData(this);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Configuration configuration = getResources().getConfiguration();
        onConfigurationChanged(configuration);

        setupObserves();
        setAdapter();
        initButtonBack();
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setAdapter() {
        mainAdapter.addDelegate(new SettingsUserItemDelegate());
        mainAdapter.addDelegate(new ServiceItemDelegate());
        mainAdapter.addDelegate(new ServiceRedItemDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
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

        viewModel.navigateToPrivacy.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_basicSettingsFragment_to_privacySettingsFragment);            }
        });

        viewModel.logoutDialog.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                LogoutDialogFragment dialogFragment = new LogoutDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "LOGOUT_DIALOG");
                DialogDismissedListener listener = bundle -> {
                    getActivity().finish();
                };
                dialogFragment.onDismissListener(listener);
            }
        });


    }
}