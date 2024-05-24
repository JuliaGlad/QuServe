package com.example.myapplication.presentation.dialogFragments.finishQueue;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogFinishQueueBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class FinishQueueDialogFragment extends DialogFragment {

    FinishQueueViewModel viewModel;

    boolean isFinished = false;
    DialogDismissedListener listener;
    String queueId, type, companyId;

    public FinishQueueDialogFragment(String queueId, String type, String companyId) {
        this.queueId = queueId;
        this.type = type;
        this.companyId = companyId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        viewModel = new ViewModelProvider(this).get(FinishQueueViewModel.class);
        DialogFinishQueueBinding binding = DialogFinishQueueBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.buttonFinish.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonFinish.setEnabled(false);
            viewModel.finishQueue(queueId, type, companyId);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isFinished){
            listener.handleDialogClose(null);
        }
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    private void setupObserves() {
        viewModel.isFinished.observe(this, aBoolean -> {
            if (aBoolean) {
                isFinished = true;
                dismiss();
            }
        });
    }

}
