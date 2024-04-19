package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.recycler;

public class IngredientToRemoveItemModel {
    int id;
    String name;

    public IngredientToRemoveItemModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean compareTo(IngredientToRemoveItemModel other){
        return this.hashCode() == other.hashCode();
    }
}
