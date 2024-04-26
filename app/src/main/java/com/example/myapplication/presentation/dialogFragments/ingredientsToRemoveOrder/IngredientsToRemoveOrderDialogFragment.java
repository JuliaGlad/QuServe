package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOrder;

import static com.example.myapplication.presentation.utils.constants.Restaurant.INGREDIENT_TO_REMOVE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.databinding.DialogIngredientsToRemoveOrderBinding;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOrder.recycler.IngredientsToRemoveOrderAdapter;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOrder.recycler.IngredientsToRemoveOrderModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class IngredientsToRemoveOrderDialogFragment extends DialogFragment {

    private DialogDismissedListener listener;
    private final List<String> toRemove;
    private final List<String> added;

    public IngredientsToRemoveOrderDialogFragment(List<String> toRemove, List<String> added) {
        this.added = added;
        this.toRemove = toRemove;
    }

    public void OnDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        DialogIngredientsToRemoveOrderBinding binding = DialogIngredientsToRemoveOrderBinding.inflate(getLayoutInflater());
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        IngredientsToRemoveOrderAdapter adapter = new IngredientsToRemoveOrderAdapter();

        List<IngredientsToRemoveOrderModel> models = new ArrayList<>();

        for (int i = 0; i < toRemove.size(); i++) {
            models.add(new IngredientsToRemoveOrderModel(i, toRemove.get(i), added));
        }

        binding.buttonAdd.setOnClickListener(v -> {
            dismiss();
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(models);

        return builder.setView(binding.getRoot()).create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && added.size() > 0) {
            listener.handleDialogClose(null);
        }
    }
}
