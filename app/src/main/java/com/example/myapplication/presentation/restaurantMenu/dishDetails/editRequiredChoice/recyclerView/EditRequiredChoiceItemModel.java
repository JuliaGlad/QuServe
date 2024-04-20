package com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.recyclerView;

import myapplication.android.ui.listeners.ButtonItemListener;

public class EditRequiredChoiceItemModel {
    int id;
    String ingredientName;
    ButtonItemListener listener;
    EditChoiceListener editListener;

    public EditRequiredChoiceItemModel(int id, String ingredientName, ButtonItemListener listener, EditChoiceListener editListener) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.listener = listener;
        this.editListener = editListener;
    }

    boolean compareTo(EditRequiredChoiceItemModel other){
        return other.hashCode() == this.hashCode();
    }
}
