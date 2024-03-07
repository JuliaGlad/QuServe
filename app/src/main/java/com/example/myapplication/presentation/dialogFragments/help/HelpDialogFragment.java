package com.example.myapplication.presentation.dialogFragments.help;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.databinding.DialogSendHelpEmailBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class HelpDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogSendHelpEmailBinding binding = DialogSendHelpEmailBinding.inflate(getLayoutInflater());

        binding.buttonSend.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"shadow_berserker@mail.ru"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "User Question");
            intent.putExtra(Intent.EXTRA_TEXT, binding.editLayoutQuestion.getText().toString());

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose mail"));
            dismiss();
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }
}
