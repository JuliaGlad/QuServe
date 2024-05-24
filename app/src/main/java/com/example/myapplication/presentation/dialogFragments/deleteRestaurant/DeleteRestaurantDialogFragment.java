package com.example.myapplication.presentation.dialogFragments.deleteRestaurant;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogDeleteCheckBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class DeleteRestaurantDialogFragment extends DialogFragment {

    private DeleteRestaurantViewModel viewModel;
    private final String restaurantId;
    private boolean isDeleted = false;
    private DialogDismissedListener listener;

    public void onDismissListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    public DeleteRestaurantDialogFragment(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DeleteRestaurantViewModel.class);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        DialogDeleteCheckBinding binding = DialogDeleteCheckBinding.inflate(getLayoutInflater());

        binding.textMain.setText(getString(R.string.are_you_sure_you_want_to_delete_your_restaurant));

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonDelete.setEnabled(false);
            viewModel.deleteRestaurant(restaurantId);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.isDeleted.observe(this, aBoolean -> {
            if (aBoolean){
                isDeleted = true;
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isDeleted){
            listener.handleDialogClose(null);
        }
    }
}
