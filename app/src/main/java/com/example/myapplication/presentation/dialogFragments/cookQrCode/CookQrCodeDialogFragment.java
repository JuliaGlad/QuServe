package com.example.myapplication.presentation.dialogFragments.cookQrCode;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.DialogCookQrCodeBinding;
import com.example.myapplication.databinding.DialogWaiterQrCodeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CookQrCodeDialogFragment extends DialogFragment {

    private final Uri uri;

    public CookQrCodeDialogFragment(Uri uri) {
        this.uri = uri;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DialogCookQrCodeBinding binding = DialogCookQrCodeBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        Glide.with(requireContext())
                .load(uri)
                .into(binding.qrCode);

        binding.buttonOk.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }
}
