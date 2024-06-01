package com.example.myapplication.presentation.dialogFragments.changeRole;

import static com.example.myapplication.presentation.utils.constants.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogChangeEmployeeRoleBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class ChangeRoleDialogFragment extends DialogFragment {

    private ChangeRoleDialogViewModel viewModel;
    private DialogChangeEmployeeRoleBinding binding;

    private final String companyId;
    private final String employeeId;
    private final String role;
    private DialogDismissedListener listener;
    private String newRole;
    private boolean isUpdated;

    public ChangeRoleDialogFragment(String companyId, String employeeId, String role) {
        this.employeeId = employeeId;
        this.companyId = companyId;
        this.role = role;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChangeRoleDialogViewModel.class);
        binding = DialogChangeEmployeeRoleBinding.inflate(getLayoutInflater());

        setupObserves();

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        binding.constraintLayoutAdmin.setOnClickListener(v -> {
            setAdminSelected();
        });

        binding.constraintLayoutWorker.setOnClickListener(v -> {
            setWorkerSelected();

        });

        binding.buttonSend.setOnClickListener(v -> {
            binding.loaderCancel.setVisibility(View.VISIBLE);
            binding.buttonSend.setEnabled(false);
            viewModel.updateField(newRole, employeeId, companyId);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        if (role.equals(ADMIN)){
            setAdminSelected();
        } else {
            setWorkerSelected();
        }

        return builder.setView(binding.getRoot()).create();
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isUpdated) {

            Bundle bundle = new Bundle();
            bundle.putString(EMPLOYEE_ROLE, newRole);
            listener.handleDialogClose(bundle);
        }
    }

    private void setupObserves() {
        viewModel.dismiss.observe(this, aBoolean -> {
            if (aBoolean){
                isUpdated = true;
                dismiss();
            }
        });
    }

    private void setWorkerSelected() {
        setSelected(
                binding.iconWorker,
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_worker, requireContext().getTheme()),
                binding.roleWorker,
                binding.firstWorkerFeature,
                binding.secondWorkerFeature,
                binding.constraintLayoutWorker
        );
        setDisabled(
                binding.iconAdmin,
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_admin_disabled, requireContext().getTheme()),
                binding.role,
                binding.firstAdminFeature,
                binding.secondAdminFeature,
                binding.constraintLayoutAdmin
        );
        newRole = WORKER;
    }

    private void setAdminSelected() {
        setSelected(
                binding.iconAdmin,
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_admin, requireContext().getTheme()),
                binding.role,
                binding.firstAdminFeature,
                binding.secondAdminFeature,
                binding.constraintLayoutAdmin
        );

        setDisabled(
                binding.iconWorker,
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_worker_disabled, requireContext().getTheme()),
                binding.roleWorker,
                binding.firstWorkerFeature,
                binding.secondWorkerFeature,
                binding.constraintLayoutWorker
        );

        newRole = ADMIN;
    }

    private void setSelected(ImageView imageView, Drawable drawable, TextView header, TextView featureFirst, TextView featureSecond, ConstraintLayout layout) {
        layout.setSelected(true);
        layout.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.role_card_background, requireContext().getTheme()));
        imageView.setImageDrawable(drawable);
        header.setTextColor(getResources().getColor(R.color.colorPrimary, requireContext().getTheme()));
        featureFirst.setTextColor(getResources().getColor(R.color.colorTextHint, requireContext().getTheme()));
        featureSecond.setTextColor(getResources().getColor(R.color.colorTextHint, requireContext().getTheme()));
    }

    private void setDisabled(ImageView imageView, Drawable drawable, TextView header, TextView featureFirst, TextView featureSecond, ConstraintLayout layout) {
        layout.setSelected(false);
        layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.role_disabled_card_background, requireContext().getTheme()));
        imageView.setImageDrawable(drawable);
        header.setTextColor(getResources().getColor(R.color.colorTextDisabled, requireContext().getTheme()));
        featureFirst.setTextColor(getResources().getColor(R.color.colorTextDisabled, requireContext().getTheme()));
        featureSecond.setTextColor(getResources().getColor(R.color.colorTextDisabled, requireContext().getTheme()));
    }
}