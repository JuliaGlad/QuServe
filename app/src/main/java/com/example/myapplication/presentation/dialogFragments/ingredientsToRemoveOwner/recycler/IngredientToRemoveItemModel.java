package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.recycler;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.ButtonStringListener;

public class IngredientToRemoveItemModel {
    int id;
    String name;
    ButtonItemListener listenerDelete;
    ButtonStringListener listenerAdded;
    ButtonStringListener listenerUpdated;

    public IngredientToRemoveItemModel(int id, String name, ButtonItemListener listenerDelete, ButtonStringListener listenerAdded, ButtonStringListener listenerUpdated) {
        this.id = id;
        this.listenerDelete = listenerDelete;
        this.listenerAdded = listenerAdded;
        this.listenerUpdated = listenerUpdated;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean compareTo(IngredientToRemoveItemModel other){
        return this.hashCode() == other.hashCode();
    }
}
