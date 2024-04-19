package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogIngredientsToRemoveBinding;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.recycler.IngredientToRemoveAdapter;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.recycler.IngredientToRemoveItemModel;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.state.IngredientToRemoveDialogState;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.listeners.DialogDismissedListener;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class IngredientsToRemoveDialogFragment extends DialogFragment {

    private IngredientsToRemoveViewModel viewModel = new IngredientsToRemoveViewModel();
    private DialogIngredientsToRemoveBinding binding;
    private String restaurantId, dishId, categoryId;
    private boolean isSaved;
    private DialogDismissedListener listener;
    private List<IngredientToRemoveItemModel> models = new ArrayList<>();
    private List<IngredientToRemoveItemModel> added = new ArrayList<>();
    private final IngredientToRemoveAdapter adapter = new IngredientToRemoveAdapter();

    public void onDismissListener(DialogDismissedListener listener){
        this.listener = listener;
    }

    public IngredientsToRemoveDialogFragment(String restaurantId, String categoryId, String dishId) {
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.dishId = dishId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(IngredientsToRemoveViewModel.class);
        viewModel.getIngredientsToRemove(restaurantId, categoryId, dishId);

        binding = DialogIngredientsToRemoveBinding.inflate(getLayoutInflater());
        binding.recyclerView.setAdapter(adapter);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        setupObserves();
        initSaveButton();
        initCancelButton();
        initDeleteButton();
        initAddButton();

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.state.observe(this, state -> {
            if (state instanceof IngredientToRemoveDialogState.Success){
                List<String> ingredients = ((IngredientToRemoveDialogState.Success)state).data;
                initRecycler(ingredients);
            } else if (state instanceof IngredientToRemoveDialogState.Loading){

            } else if (state instanceof IngredientToRemoveDialogState.Error){

            }
        });

        viewModel.isSaved.observe(this, aBoolean -> {
            if (aBoolean){
                isSaved = true;
                dismiss();
            }
        });
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            List<IngredientToRemoveItemModel> newModels = new ArrayList<>(models);
            newModels.add(new IngredientToRemoveItemModel(models.size(), null));
            adapter.submitList(newModels);
            models = newModels;
        });
    }

    private void initDeleteButton() {
        binding.buttonDelete.setOnClickListener(v -> {

        });
    }

    private void initRecycler(List<String> ingredients) {
        if (ingredients.size() > 0) {
            for (int i = 0; i < ingredients.size(); i++) {
                models.add(new IngredientToRemoveItemModel(i, ingredients.get(i)));
            }
            adapter.submitList(models);
        }
    }

    private void initCancelButton() {
        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    private void initSaveButton() {
        binding.buttonSave.setOnClickListener(v -> {

         //   viewModel.addItems(restaurantId, categoryId, dishId, );
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null && isSaved){
            listener.handleDialogClose(null);
        }
    }
}
