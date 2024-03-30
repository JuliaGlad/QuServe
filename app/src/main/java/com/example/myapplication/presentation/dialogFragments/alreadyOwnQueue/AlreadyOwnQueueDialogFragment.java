package com.example.myapplication.presentation.dialogFragments.alreadyOwnQueue;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.DialogAlreadyOwnQueueBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class AlreadyOwnQueueDialogFragment extends DialogFragment {

    DialogAlreadyOwnQueueBinding binding;
    AlreadyOwnQueueViewModel viewModel;
    DialogDismissedListener listener;
    private boolean isFinished = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(AlreadyOwnQueueViewModel.class);
        binding = DialogAlreadyOwnQueueBinding.inflate(getLayoutInflater());

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        setupObserves();
        viewModel.getQueueData();

        binding.buttonFinish.setOnClickListener(v -> {
            viewModel.finishQueue();
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
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

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isFinished){
            listener.handleDialogClose(null);
        }
    }

}