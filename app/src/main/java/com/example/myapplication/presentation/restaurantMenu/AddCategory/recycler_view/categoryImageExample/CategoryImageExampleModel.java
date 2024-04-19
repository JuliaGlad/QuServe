package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageExample;

public class CategoryImageExampleModel {
    int id;
    int drawable;
    ImageExampleListener listener;

    public CategoryImageExampleModel(int id, int drawable, ImageExampleListener listener) {
        this.id = id;
        this.drawable = drawable;
        this.listener = listener;
    }

    boolean compareTo(CategoryImageExampleModel other){
       return other.hashCode() == this.hashCode();
    }
}
