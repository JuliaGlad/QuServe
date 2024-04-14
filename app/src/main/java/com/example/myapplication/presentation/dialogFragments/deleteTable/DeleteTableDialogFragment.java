package com.example.myapplication.presentation.dialogFragments.deleteTable;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogDeleteTableBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class DeleteTableDialogFragment extends DialogFragment {

    private DeleteTableViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean isDeleted = false;
    private final String tableId;
    private final String restaurantId;
    private final String locationId;

    public void onDialogDismissedListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    public DeleteTableDialogFragment(String tableId, String restaurantId, String locationId) {
        this.tableId = tableId;
        this.restaurantId = restaurantId;
        this.locationId = locationId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DeleteTableViewModel.class);
        DialogDeleteTableBinding binding = DialogDeleteTableBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            viewModel.deleteTable(restaurantId, locationId, tableId);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.isDeleted.observe(getViewLifecycleOwner(), aBoolean -> {
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
