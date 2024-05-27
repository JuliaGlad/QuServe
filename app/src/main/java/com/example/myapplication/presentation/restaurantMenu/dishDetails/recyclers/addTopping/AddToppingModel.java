package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping;

import java.util.Objects;

import myapplication.android.ui.listeners.ButtonItemListener;

public class AddToppingModel {
    int id;
    ButtonItemListener listener;

    public AddToppingModel(int id, ButtonItemListener listener) {
        this.id = id;
        this.listener = listener;
    }

    @Override
    public boolean equals(Object o) {
         if (!(o instanceof AddToppingModel)) return false;
        AddToppingModel that = (AddToppingModel) o;
        return id == that.id && Objects.equals(listener, that.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, listener);
    }
}
