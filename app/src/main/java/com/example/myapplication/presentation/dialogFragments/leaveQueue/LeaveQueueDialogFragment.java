package com.example.myapplication.presentation.dialogFragments.leaveQueue;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogLeaveQueueBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class LeaveQueueDialogFragment extends DialogFragment {

    private LeaveQueueDialogViewModel viewModel;
    private DialogDismissedListener listener;
    private final String queueId;
    private boolean isLeft;

    public LeaveQueueDialogFragment(String queueId) {
        this.queueId = queueId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(LeaveQueueDialogViewModel.class);

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogLeaveQueueBinding binding = DialogLeaveQueueBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.buttonLeave.setOnClickListener(v -> {
            viewModel.leaveQueue(queueId);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    public void onDismissListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isLeft){
            listener.handleDialogClose(null);
        }
    }

    private void setupObserves() {
        viewModel.isLeft.observe(this, aBoolean -> {
            if (aBoolean){
                isLeft = true;
                dismiss();
            }
        });
    }
}