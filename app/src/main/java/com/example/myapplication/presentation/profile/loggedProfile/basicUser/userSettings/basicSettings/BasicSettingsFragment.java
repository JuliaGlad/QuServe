package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings;

import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBasicSettingsBinding;
import com.example.myapplication.presentation.dialogFragments.aboutUs.AboutUsDialogFragment;
import com.example.myapplication.presentation.dialogFragments.haveAnonymousActions.HaveActionsDialogFragment;
import com.example.myapplication.presentation.dialogFragments.help.HelpDialogFragment;
import com.example.myapplication.presentation.dialogFragments.logout.LogoutDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.model.SettingsUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.state.BasicSettingsState;
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

public class BasicSettingsFragment extends Fragment {

    private BasicSettingsViewModel viewModel;
    private FragmentBasicSettingsBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private final List<DelegateItem> delegates = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BasicSettingsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasicSettingsBinding.inflate(inflater, container, false);
        viewModel.retrieveUserData();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupObserves();
        setAdapter();
        initButtonBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
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
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof BasicSettingsState.Success) {
                if (delegates.isEmpty()) {
                    SettingsUserModel model = ((BasicSettingsState.Success) state).data;
                    initRecycler(model);
                }
            } else if (state instanceof BasicSettingsState.Loading) {
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof BasicSettingsState.Error) {
                setErrorLayout();
            }
        });

        viewModel.haveActions.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null){
                if (!aBoolean){
                    LogoutDialogFragment dialogFragment = new LogoutDialogFragment();
                    dialogFragment.show(requireActivity().getSupportFragmentManager(), "LOGOUT_DIALOG");
                    DialogDismissedListener listener = bundle -> {

                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
                        sharedPreferences.edit().putString(APP_STATE, ANONYMOUS).apply();
                        sharedPreferences.edit().putString(COMPANY_ID, null).apply();

                        requireActivity().setResult(Activity.RESULT_OK);
                        requireActivity().finish();
                    };
                    dialogFragment.onDismissListener(listener);
                } else {
                    HaveActionsDialogFragment dialogFragment = new HaveActionsDialogFragment();
                    dialogFragment.show(requireActivity().getSupportFragmentManager(), "HAVE_ACTIONS_DIALOG");
                }
            }
        });

    }

    private void initRecycler(SettingsUserModel model) {
        delegates.add(new SettingsUserItemDelegateItem(new SettingsUserItemModel(1, model.getName(), model.getEmail(), model.getUri())));
        delegates.add(new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_shield_person, R.string.privacy_and_security, () -> {
            viewModel.setArgumentsNull();
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_basicSettingsFragment_to_privacySettingsFragment);
        })));
        delegates.add(new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_help, R.string.help, () -> {
            HelpDialogFragment dialogFragment = new HelpDialogFragment();
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "HELP_DIALOG");
        })));
        delegates.add(new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_group, R.string.about_us, () -> {
            AboutUsDialogFragment dialogFragment = new AboutUsDialogFragment();
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "ABOUT_US_DIALOG");
        })));

        delegates.add(new ServiceRedItemDelegateItem(new ServiceRedItemModel(5, R.drawable.ic_logout, R.string.logout, () -> {
            viewModel.getUserActions();
        })));

        mainAdapter.submitList(delegates);
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.GONE);
    }

    private void setErrorLayout() {
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.retrieveUserData();
        });
    }

}