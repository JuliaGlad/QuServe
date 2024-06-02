package com.example.myapplication.presentation.dialogFragments.deleteToppingDialog;

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

public class DeleteToppingDialogFragment extends DialogFragment {

    private DeleteToppingViewModel viewModel;
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
        DialogDeleteCheckBinding binding = DialogDeleteCheckBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        binding.textMain.setText(getString(R.string.are_you_sure_you_want_to_delete_this_topping_from_this_dish));

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonDelete.setEnabled(false);
            binding.buttonDelete.setText("");
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
