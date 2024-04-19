package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.requiredChoice;

public class RequiredChoiceItemModel {
    int id;
    String name;

    public RequiredChoiceItemModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean compareTo(RequiredChoiceItemModel other){
        return this.hashCode() == other.hashCode();
    }
}
