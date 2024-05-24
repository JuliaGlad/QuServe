package com.example.myapplication.presentation.dialogFragments.alreadyParticipateInQueue;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogAlreadyParticipateBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class AlreadyParticipateInQueueDialogFragment extends DialogFragment {

    private AlreadyParticipateInQueueDialogViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean isLeft = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(AlreadyParticipateInQueueDialogViewModel.class);
        DialogAlreadyParticipateBinding binding = DialogAlreadyParticipateBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        setupObserves();

        binding.buttonLeave.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonLeave.setEnabled(false);
            viewModel.leaveQueue();
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.isLeft.observe(this, aBoolean -> {
            if (aBoolean){
                isLeft = true;
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
        if (listener != null && isLeft){
            listener.handleDialogClose(null);
        }
    }
}