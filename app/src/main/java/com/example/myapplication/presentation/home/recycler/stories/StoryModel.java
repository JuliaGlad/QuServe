package com.example.myapplication.presentation.home.recycler.stories;

import myapplication.android.ui.listeners.ButtonItemListener;

public class StoryModel {
    int id;
    String title;
    int background;
    int image;
    ButtonItemListener listener;

    public StoryModel(int id, String title, int background, int image,  ButtonItemListener listener) {
        this.id = id;
        this.title = title;
        this.background = background;
        this.listener = listener;
        this.image = image;
    }

    public boolean compareTo(StoryModel other){
        return this.hashCode() == other.hashCode();
    }
}
