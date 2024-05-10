package com.example.myapplication.presentation.service.basicUser.recyclerView;

import androidx.activity.result.ActivityResultLauncher;

import com.journeyapps.barcodescanner.ScanOptions;

public class ButtonWithDescriptionModel {
    private final int id;
    private final int drawable;
    private final int title;
    private final int description;
    private final ActivityResultLauncher<ScanOptions> launcher;

    public ButtonWithDescriptionModel(int id, int drawable, int title, int description, ActivityResultLauncher<ScanOptions> launcher) {
        this.id = id;
        this.drawable = drawable;
        this.title = title;
        this.description = description;
        this.launcher = launcher;
    }

    public int getId() {
        return id;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getTitle() {
        return title;
    }

    public int getDescription() {
        return description;
    }

    public ActivityResultLauncher<ScanOptions> getLauncher() {
        return launcher;
    }

    public boolean compareTo(ButtonWithDescriptionModel other){
        return other.hashCode() == this.hashCode();
    }
}
