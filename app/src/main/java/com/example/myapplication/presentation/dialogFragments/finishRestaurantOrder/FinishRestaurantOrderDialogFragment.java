package com.example.myapplication.presentation.dialogFragments.finishRestaurantOrder;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogFinishRestaurantOrderBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class FinishRestaurantOrderDialogFragment extends DialogFragment {

    private FinishRestaurantOrderViewModel viewModel;
    private final String orderPath, tableId;
    private boolean isFinished = false;
    private DialogDismissedListener listener;

    public void onDialogDismissedListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    public FinishRestaurantOrderDialogFragment(String orderPath, String tableId) {
        this.orderPath = orderPath;
        this.tableId = tableId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FinishRestaurantOrderViewModel.class);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogFinishRestaurantOrderBinding binding = DialogFinishRestaurantOrderBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });
        binding.buttonFinish.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonFinish.setEnabled(false);
            viewModel.finishOrder(orderPath, tableId, false);
        });

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.isFinished.observe(this, aBoolean -> {
            if (aBoolean){
                isFinished = true;
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isFinished){
            listener.handleDialogClose(null);
        }
    }
}
