package com.example.myapplication.presentation.dialogFragments.stopPause;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogStopPauseBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class StopPauseDialogFragment extends DialogFragment {

    private StopPauseViewModel viewModel;
    private DialogDismissedListener listener;
    private final String type;
    private final String companyId;
    private final String queueId;
    private boolean isStopped;

    public void onDismissListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    public StopPauseDialogFragment(String queueId, String companyId, String type) {
        this.queueId = queueId;
        this.companyId = companyId;
        this.type = type;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(StopPauseViewModel.class);
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogStopPauseBinding binding = DialogStopPauseBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.buttonStop.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonStop.setEnabled(false);
            binding.buttonStop.setText("");
            viewModel.continueQueue(queueId, companyId, type);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isStopped){
            listener.handleDialogClose(null);
        }
    }

    private void setupObserves(){
        viewModel.isStopped.observe(this, aBoolean -> {
            if (aBoolean){
                isStopped = true;
                dismiss();
            }
        });
    }
}
