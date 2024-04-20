package com.example.myapplication.presentation.dialogFragments.addNewVariant;

import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_NAME;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogAddVariantLayoutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class AddNewVariantDialogFragment extends DialogFragment {

    private AddNewVariantViewModel viewModel;
    private DialogDismissedListener listener;
    private String restaurantId, categoryId, dishId, choiceId;
    private String addedName;

    public AddNewVariantDialogFragment(String restaurantId, String categoryId, String dishId, String choiceId) {
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.dishId = dishId;
        this.choiceId = choiceId;
    }

    public void onDismissListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddNewVariantViewModel.class);
        DialogAddVariantLayoutBinding binding = DialogAddVariantLayoutBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        setupObserves();

        binding.buttonAdd.setOnClickListener(v -> {
            String newVariant = binding.editLayoutVariant.getText().toString();
            viewModel.addVariant(restaurantId, categoryId, dishId, choiceId, newVariant);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.isAdded.observe(this, name -> {
            if (name != null){
                addedName = name;
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (addedName != null && listener != null){

            Bundle bundle = new Bundle();
            bundle.putString(CHOICE_NAME, addedName);

            listener.handleDialogClose(bundle);
        }
    }
}
