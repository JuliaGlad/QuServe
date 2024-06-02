package com.example.myapplication.presentation.dialogFragments.deleteCompany;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogDeleteCheckBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class DeleteCompanyDialogFragment extends DialogFragment {

    private DeleteCompanyDialogViewModel viewModel;
    private DialogDeleteCheckBinding binding;
    private DialogDismissedListener listener;
    private boolean isDeleted = false;

    private final String companyId;

    public DeleteCompanyDialogFragment(String companyId) {
        this.companyId = companyId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        viewModel = new ViewModelProvider(this).get(DeleteCompanyDialogViewModel.class);
        binding = DialogDeleteCheckBinding.inflate(getLayoutInflater());

        binding.textMain.setText(getString(R.string.are_you_sure_you_want_to_delete_your_company));

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonDelete.setEnabled(false);
            binding.buttonDelete.setText("");
            viewModel.deleteCompany(companyId);
        });

        binding.buttonCancel.setOnClickListener(v -> dismiss());

        return builder.setView(binding.getRoot()).create();

    }

    private void setupObserves() {
        viewModel.isDeleted.observe(this, aBoolean -> {
            if (aBoolean){
                isDeleted = true;
                dismiss();
            }
        });
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isDeleted) {
            listener.handleDialogClose(null);
        }
    }

}