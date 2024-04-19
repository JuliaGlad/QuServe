package com.example.myapplication.presentation.dialogFragments.deleteToppingDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogDeleteToppingBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class DeleteToppingDialogFragment extends DialogFragment {

    private DeleteToppingViewModel viewModel;
    private DialogDeleteToppingBinding binding;
    private DialogDismissedListener listener;
    private String restaurantId, categoryId, dishId, name;
    private boolean isDeleted = false;

    public void onDismissListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    public DeleteToppingDialogFragment(String restaurantId, String categoryId, String dishId, String name) {
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.name = name;
        this.dishId = dishId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DeleteToppingViewModel.class);
        binding = DialogDeleteToppingBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            viewModel.deleteTopping(restaurantId, categoryId, dishId, name);
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
