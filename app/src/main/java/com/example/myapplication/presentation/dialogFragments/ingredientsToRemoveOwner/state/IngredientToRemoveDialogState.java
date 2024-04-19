package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.state;

import java.util.List;

public interface IngredientToRemoveDialogState {
    class Success implements IngredientToRemoveDialogState{
        public List<String> data;

        public Success(List<String> data) {
            this.data = data;
        }
    }
    class Loading implements IngredientToRemoveDialogState{}

    class Error implements IngredientToRemoveDialogState{}
}
