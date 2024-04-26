package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOrder.recycler;

import java.util.ArrayList;
import java.util.List;

public class IngredientsToRemoveOrderModel {
    int id;
    String name;
    boolean isAdded = false;
    List<String> added;

    public IngredientsToRemoveOrderModel(int id, String name, List<String> added) {
        this.id = id;
        this.name = name;
        this.added = added;
    }

    public boolean compareTo(IngredientsToRemoveOrderModel other){
        return this.hashCode() == other.hashCode();
    }
}
