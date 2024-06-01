package com.example.myapplication.presentation.dialogFragments.wronQrCode;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.myapplication.databinding.DialogWrongQrCodeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class WrongQrCodeDialogFragment extends DialogFragment {

    private DialogDismissedListener listener;

    public void onDialogDismissedListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DialogWrongQrCodeBinding binding = DialogWrongQrCodeBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        binding.buttonOk.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null){
            listener.handleDialogClose(null);
        }
    }
}
