package com.example.myapplication.presentation.dialogFragments.deleteRestaurant;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogDeleteRestaurantBinding;
import com.github.dhaval2404.imagepicker.listener.DismissListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import io.reactivex.rxjava3.schedulers.Schedulers;
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
        DialogDeleteRestaurantBinding binding = DialogDeleteRestaurantBinding.inflate(getLayoutInflater());

        setupObserves();

        binding.buttonDelete.setOnClickListener(v -> {
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
