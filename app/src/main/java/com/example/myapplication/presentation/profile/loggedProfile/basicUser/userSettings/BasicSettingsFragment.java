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

import com.example.myapplication.databinding.FragmentBasicSettingsBinding;
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
    }

//    private void initLogoutButton() {
//        binding.constraintSettingsLogout.setOnClickListener(view -> {
//            final View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout_layout, null);
//            AlertDialog logoutDialog = new AlertDialog.Builder(getContext())
//                    .setView(dialogView).create();
//
//            logoutDialog.show();
//
//            Button logout = dialogView.findViewById(R.id.logout_button);
//            Button cancel = dialogView.findViewById(R.id.cancel_button);
//            logout.setOnClickListener(viewLogout -> {
//                viewModel.logout();
//                logoutDialog.dismiss();
//                navigateToLogin();
//            });
//
//            cancel.setOnClickListener(viewCancel -> {
//                logoutDialog.dismiss();
//            });
//
//        });
//    }
//
//    private void initUpdatePassword() {
//        binding.constraintChangePasswordPrivacy.setOnClickListener(click -> {
//            showUpdatePasswordDialog();
//        });
//    }
//
//    private void setupChangePasswordObserves() {
//        viewModel.updatePassword.observe(getViewLifecycleOwner(), aBoolean -> {
//            if (aBoolean) {
//                passwordUpdateDialog.dismiss();
//                showSuccessDialog();
//            } else {
//                Toast.makeText(getContext(), getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
//    //UPDATE EMAIL
//    private void setupChangeEmailObserve() {
//
//        viewModel.isSuccessful.observe(getViewLifecycleOwner(), aBoolean -> {
//            if (aBoolean) {
//                showVerificationDialog();
//            } else {
//                createFailureDialog();
//            }
//        });
//
//        viewModel.verified.observe(getViewLifecycleOwner(), aBoolean -> {
//            if (aBoolean) {
//                showSuccessDialog();
//                viewModel.updateEmailField(email, password);
//            } else {
//                createFailureDialog();
//            }
//        });
//
//        viewModel.updateEmailField.observe(getViewLifecycleOwner(), aBoolean -> {
//            if (aBoolean) {
//                showSuccessDialog();
//            } else {
//                createFailureDialog();
//            }
//        });
//    }
//
//    private void initChangeEmail() {
//        binding.constraintChangeEmailPrivacy.setOnClickListener(view -> {
//            showChangeEmailDialog();
//        });
//    }
//
//    private void showChangeEmailDialog() {
//        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_email_layout, null);
//        changeEmailDialog = new AlertDialog.Builder(getContext())
//                .setView(dialogView).create();
//
//        send = dialogView.findViewById(R.id.send_button);
//        Button cancelEmail = dialogView.findViewById(R.id.cancel_button);
//        emailEd = dialogView.findViewById(R.id.edit_new_email);
//        passwordEmailEd = dialogView.findViewById(R.id.edit_password);
//
//        setupChangeEmailObserve();
//
//        send.setOnClickListener(v -> {
//            email = emailEd.getText().toString().trim();
//            password = passwordEmailEd.getText().toString().trim();
//
//            viewModel.changeEmail(email);
//
//            changeEmailDialog.dismiss();
//        });
//
//        cancelEmail.setOnClickListener(v -> {
//            changeEmailDialog.dismiss();
//        });
//
//        changeEmailDialog.show();
//    }
//
//    private void showVerificationDialog() {
//        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_verification_email_update_layout, null);
//        AlertDialog verification = new AlertDialog.Builder(getContext())
//                .setView(dialogView).create();
//
//        verification.show();
//
//        yourEmail = dialogView.findViewById(R.id.your_email);
//        Button done = dialogView.findViewById(R.id.done_button);
//
//        yourEmail.setText(email);
//
//        done.setOnClickListener(v -> {
//            viewModel.checkVerification(email, password);
//            verification.dismiss();
//        });
//    }
//
//    private void showUpdatePasswordDialog() {
//        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_password_layout, null);
//        passwordUpdateDialog = new AlertDialog.Builder(getContext())
//                .setView(dialogView).create();
//
//        oldPasswordEd = dialogView.findViewById(R.id.edit_old_password);
//        newPasswordEd = dialogView.findViewById(R.id.edit_new_password);
//        update = dialogView.findViewById(R.id.update_password_button);
//        Button cancelPassword = dialogView.findViewById(R.id.cancel_button);
//
//        setupChangePasswordObserves();
//
//        passwordUpdateDialog.show();
//
//        cancelPassword.setOnClickListener(v -> {
//            passwordUpdateDialog.dismiss();
//        });
//
//        update.setOnClickListener(view -> {
//
//            oldPassword = oldPasswordEd.getText().toString().trim();
//            newPassword = newPasswordEd.getText().toString().trim();
//            viewModel.updatePassword(oldPassword, newPassword);
//        });
//    }
//

//
//    //DELETE ACCOUNT
//    private void initDeleteAccount() {
//        binding.constraintPrivacyDelete.setOnClickListener(v -> {
//            showDeleteDialog();
//        });
//    }
//
//    private void showDeleteDialog() {
//        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete_layout, null);
//        deleteDialog = new AlertDialog.Builder(getContext())
//                .setView(dialogView).create();
//
//        delete = dialogView.findViewById(R.id.delete_button);
//        cancelDelete = dialogView.findViewById(R.id.cancel_button);
//
//        cancelDelete.setOnClickListener(v -> {
//            deleteDialog.dismiss();
//        });
//        initDeleteButton();
//
//        deleteDialog.show();
//    }
//
//    private void initDeleteButton() {
//        delete.setOnClickListener(v -> {
//            viewModel.deleteAccount();
//            deleteDialog.dismiss();
//            navigateToLogin();
//        });
//    }
//
//    //COMMON
//    private void initOkButton() {
//        ok.setOnClickListener(v -> {
//            successUpdateDialog.dismiss();
//        });
//    }
//
//    private void showSuccessDialog() {
//        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_email_update_successful, null);
//        successUpdateDialog = new AlertDialog.Builder(getContext())
//                .setView(dialogView).create();
//
//        ok = dialogView.findViewById(R.id.ok_button);
//
//        successUpdateDialog.show();
//
//        initOkButton();
//    }
//
//    private void createFailureDialog() {
//        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_verification_failed, null);
//        AlertDialog failedDialog = new AlertDialog.Builder(getContext())
//                .setView(dialogView).create();
//
//        failedDialog.show();
//
//        Button okayButton = dialogView.findViewById(R.id.ok_button);
//        okayButton.setOnClickListener(view -> {
//            failedDialog.dismiss();
//        });
//    }
//
//    private void navigateToLogin(){
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentManager.popBackStack();
//    }
//    private void createHelpDialog(){
//
//        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_send_help_email, null);
//        AlertDialog helpDialog = new AlertDialog.Builder(getContext())
//                .setView(dialogView).create();
//
//        cancel = dialogView.findViewById(R.id.cancel_button);
//
//        setupHelpObserves();
//
//        initCancelButton(cancel, helpDialog);
//        helpDialog.show();
//    }

//    private void initCancelButton(Button button, AlertDialog alertDialog){
//        button.setOnClickListener(view -> {
//            alertDialog.dismiss();
//        });
//    }
    //    private void initHelpButton() {
//        binding.constraintSettingsHelp.setOnClickListener(v -> {
//            createHelpDialog();
//        });
//    }
//    private void setupHelpObserves(){
//        viewModel.helpUserEmail.observe(getViewLifecycleOwner(), string -> {
//            helpUserEmail = string;
//        });
//    }
}