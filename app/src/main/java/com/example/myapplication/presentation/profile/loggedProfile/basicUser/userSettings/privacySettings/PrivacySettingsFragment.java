package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.privacySettings;

import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.EMAIL;
import static com.example.myapplication.presentation.utils.constants.Utils.PASSWORD;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentPrivacySettingsBinding;
import com.example.myapplication.presentation.dialogFragments.changeEmail.ChangeEmailDialogFragment;
import com.example.myapplication.presentation.dialogFragments.deleteAccount.DeleteAccountDialogFragment;
import com.example.myapplication.presentation.dialogFragments.emailUpdateSuccessful.EmailUpdateSuccessfulDialogFragment;
import com.example.myapplication.presentation.dialogFragments.haveAnonymousActions.HaveActionsDialogFragment;
import com.example.myapplication.presentation.dialogFragments.passwordUpdateSuccessful.PasswordUpdateSuccessfulDialogFragment;
import com.example.myapplication.presentation.dialogFragments.updatePasswordDialog.UpdatePasswordDialogFragment;
import com.example.myapplication.presentation.dialogFragments.verifyBeforeUpdateDialogFragment.VerifyBeforeUpdateDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemModel;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.listeners.DialogDismissedListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class PrivacySettingsFragment extends Fragment {

    private PrivacySettingsViewModel viewModel;
    private FragmentPrivacySettingsBinding binding;
    private String email, password;
    private final List<DelegateItem> delegates = new ArrayList<>();
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(PrivacySettingsViewModel.class);
        binding = FragmentPrivacySettingsBinding.inflate(inflater, container, false);
        initRecycler();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setupObserves();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initRecycler(){
        delegates.add(new ServiceItemDelegateItem(new ServiceItemModel(1, R.drawable.ic_mail, R.string.change_email, () -> {
            ChangeEmailDialogFragment dialogFragment = new ChangeEmailDialogFragment();
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "CHANGE_EMAIL_DIALOG");
            DialogDismissedListener listener = bundle -> {

                email = bundle.getString(EMAIL);
                password = bundle.getString(PASSWORD);
                viewModel.verifyBeforeUpdate(email);

            };
            dialogFragment.onDismissListener(listener);
        })));
        delegates.add(new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_lock_reset, R.string.update_password, () -> {

            UpdatePasswordDialogFragment dialogFragment = new UpdatePasswordDialogFragment();
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "UPDATE_PASSWORD_DIALOG");
            DialogDismissedListener listener = bundle -> {
                PasswordUpdateSuccessfulDialogFragment successDialog = new PasswordUpdateSuccessfulDialogFragment();
                successDialog.show(requireActivity().getSupportFragmentManager(), "SUCCESS_DIALOG");
            };
            dialogFragment.onDismissListener(listener);

        })));
        delegates.add(new ServiceRedItemDelegateItem(new ServiceRedItemModel(3, R.drawable.ic_delete, R.string.delete_account, () -> {
            viewModel.getUserActions();

        })));
        mainAdapter.submitList(delegates);
    }

    private void setAdapter(){
        mainAdapter.addDelegate(new ServiceItemDelegate());
        mainAdapter.addDelegate(new ServiceRedItemDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.openSuccessDialog.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                EmailUpdateSuccessfulDialogFragment dialogFragment = new EmailUpdateSuccessfulDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "SUCCESS_DIALOG");
            }
        });

        viewModel.haveActions.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null){
                if (!aBoolean){
                    DeleteAccountDialogFragment dialogFragment = new DeleteAccountDialogFragment();
                    dialogFragment.show(requireActivity().getSupportFragmentManager(), "DELETE_ACCOUNT_DIALOG");
                    DialogDismissedListener listener = bundle -> {
                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString(APP_STATE, ANONYMOUS).apply();
                        requireActivity().finish();
                    };
                    dialogFragment.onDismissListener(listener);
                }else {
                    HaveActionsDialogFragment dialogFragment = new HaveActionsDialogFragment();
                    dialogFragment.show(requireActivity().getSupportFragmentManager(), "HAVE_ACTIONS_DIALOG");
                }
            }
        });

        viewModel.openVerifyDialog.observe(getViewLifecycleOwner(), aBoolean -> {
            VerifyBeforeUpdateDialogFragment dialogFragment = new VerifyBeforeUpdateDialogFragment(email, password);
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "VERIFY_BEFORE_UPDATE_DIALOG");
            DialogDismissedListener dismissedListener = object -> {
                viewModel.updateEmailField(email);
            };
            dialogFragment.onDismissListener(dismissedListener);
        });
    }
}