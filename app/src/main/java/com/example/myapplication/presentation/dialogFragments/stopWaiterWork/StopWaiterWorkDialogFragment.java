package com.example.myapplication.presentation.dialogFragments.stopWaiterWork;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogStopWaiterWorkBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class StopWaiterWorkDialogFragment extends DialogFragment {

    private StopWaiterWorkViewModel viewModel;
    private DialogDismissedListener listener;
    private final String restaurantId;
    private String locationId;
    private boolean isStarted = false;

    public StopWaiterWorkDialogFragment(String restaurantId, String locationId) {
        this.restaurantId = restaurantId;
        this.locationId = locationId;
    }

    public void onDialogDismissedListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(StopWaiterWorkViewModel.class);
        DialogStopWaiterWorkBinding binding = DialogStopWaiterWorkBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        setupObserves();

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        binding.buttonStop.setOnClickListener(v -> {
            viewModel.updateIsWorking(restaurantId, locationId);
        });

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.isStopped.observe(this, aBoolean -> {
            if (aBoolean){
                isStarted = true;
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isStarted){
            listener.handleDialogClose(null);
        }
    }
}
