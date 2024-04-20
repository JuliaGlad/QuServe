package com.example.myapplication.presentation.dialogFragments.deleteWorkerFromQueue;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogDeleteCheckBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class DeleteWorkerFromQueueDialogFragment extends DialogFragment {

    DeleteWorkerFromQueueViewModel viewModel;
    private DialogDismissedListener listener;
    private boolean isDeleted;
    private final String companyId;
    private final String queueId;
    private final String employeeId;

    public DeleteWorkerFromQueueDialogFragment(String companyId, String queueId, String employeeId) {
        this.companyId = companyId;
        this.queueId = queueId;
        this.employeeId = employeeId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(DeleteWorkerFromQueueViewModel.class);
        DialogDeleteCheckBinding binding = DialogDeleteCheckBinding.inflate(getLayoutInflater());
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        binding.textMain.setText(getString(R.string.are_you_sure_you_want_to_delete_this_worker_from_your_queue));

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            viewModel.deleteFromQueue(companyId, queueId, employeeId);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isDeleted){
            listener.handleDialogClose(null);
        }
    }

    public void onDismissListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    private void setupObserves(){
        viewModel.isDeleted.observe(this, aBoolean -> {
            if (aBoolean){
                isDeleted = true;
                dismiss();
            }
        });
    }
}
