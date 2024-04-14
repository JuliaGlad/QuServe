package com.example.myapplication.presentation.dialogFragments.waiterQrCode;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.DialogWaiterQrCodeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class WaiterQrCodeDialogFragment extends DialogFragment {

    private final Uri uri;

    public WaiterQrCodeDialogFragment(Uri uri) {
        this.uri = uri;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DialogWaiterQrCodeBinding binding = DialogWaiterQrCodeBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        Glide.with(requireView())
                .load(uri)
                .into(binding.qrCode);

        binding.buttonOk.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

}
