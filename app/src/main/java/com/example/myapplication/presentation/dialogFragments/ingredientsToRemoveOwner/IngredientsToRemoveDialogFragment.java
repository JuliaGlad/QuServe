package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner;

import android.app.Dialog;
import android.os.Bundle;

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
import java.util.List;

public class IngredientsToRemoveDialogFragment extends DialogFragment {

    private IngredientsToRemoveViewModel viewModel = new IngredientsToRemoveViewModel();
    private DialogIngredientsToRemoveBinding binding;
    private final String restaurantId;
    private final String dishId;
    private final String categoryId;
    private List<IngredientToRemoveItemModel> models = new ArrayList<>();
    private final IngredientToRemoveAdapter adapter = new IngredientToRemoveAdapter();


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
        initAddButton();

        return builder.setView(binding.getRoot()).create();
    }

    private void setupObserves() {
        viewModel.state.observe(this, state -> {
            if (state instanceof IngredientToRemoveDialogState.Success) {
                List<String> ingredients = ((IngredientToRemoveDialogState.Success) state).data;
                initRecycler(ingredients);
            } else if (state instanceof IngredientToRemoveDialogState.Loading) {

            } else if (state instanceof IngredientToRemoveDialogState.Error) {

            }
        });

        viewModel.isAdded.observe(this, name -> {
            if (name != null) {
                addNewItem(name);
            }
        });

        viewModel.isDeleted.observe(this, integer -> {
            if (integer != null){
                int index = integer;
                models.remove(index);
                adapter.notifyItemRemoved(index);
            }
        });
    }

    private void addNewItem(String name) {
        binding.buttonSave.setEnabled(true);
        binding.buttonAdd.setEnabled(true);
        List<IngredientToRemoveItemModel> newModels = new ArrayList<>(models);
        int index = models.size() - 1;
        newModels.remove(index);
        addItem(newModels, name, index);
        adapter.submitList(newModels);
        models = newModels;
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            binding.buttonAdd.setEnabled(false);
            List<IngredientToRemoveItemModel> newModels = new ArrayList<>(models);
            newModels.add(new IngredientToRemoveItemModel(models.size(), null, null, name -> {
                binding.buttonSave.setEnabled(false);
                viewModel.addItem(restaurantId, categoryId, dishId, name);
            }, null));
            adapter.submitList(newModels);
            models = newModels;
        });
    }

    private void initRecycler(List<String> ingredients) {
        if (ingredients.size() > 0) {
            for (int i = 0; i < ingredients.size(); i++) {
                addItem(models, ingredients.get(i), i);
            }
            adapter.submitList(models);
        }
    }

    private void addItem(List<IngredientToRemoveItemModel> models, String ingredient, int index) {
        final String[] name = {ingredient};
        models.add(new IngredientToRemoveItemModel(index, ingredient,
                () -> {
                    viewModel.deleteIngredient(restaurantId, categoryId, dishId, name[0], index);
                },
                null,
                nameString -> {
                    viewModel.updateIngredientToRemove(restaurantId, categoryId, dishId, name[0], nameString);
                    name[0] = nameString;
                }));
    }

    private void initSaveButton() {
        binding.buttonSave.setOnClickListener(v -> {
            dismiss();
        });
    }
}
