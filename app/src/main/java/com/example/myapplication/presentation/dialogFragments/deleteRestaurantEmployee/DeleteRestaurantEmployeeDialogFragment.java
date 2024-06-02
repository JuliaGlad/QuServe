package com.example.myapplication.presentation.dialogFragments.deleteRestaurantEmployee;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.example.myapplication.databinding.DialogDeleteRestaurantEmployeeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class DeleteRestaurantEmployeeDialogFragment extends DialogFragment {

    private DeleteRestaurantEmployeeDialogViewModel viewModel;
    private DialogDismissedListener listener;
    private String restaurantId, locationId, userId, role;
    private boolean isDeleted;

    public DeleteRestaurantEmployeeDialogFragment(String restaurantId, String locationId, String userId, String role) {
        this.restaurantId = restaurantId;
        this.locationId = locationId;
        this.userId = userId;
        this.role = role;
    }

    public void onDialogDismissedListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DialogDeleteRestaurantEmployeeBinding binding = DialogDeleteRestaurantEmployeeBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        viewModel = new ViewModelProvider(this).get(DeleteRestaurantEmployeeDialogViewModel.class);
        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonDelete.setEnabled(false);
            binding.buttonDelete.setText("");
            viewModel.deleteEmployee(restaurantId, locationId, userId, role);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isDeleted){
            listener.handleDialogClose(null);
        }
    }
}