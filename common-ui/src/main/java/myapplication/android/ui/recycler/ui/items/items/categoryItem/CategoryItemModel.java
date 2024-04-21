package myapplication.android.ui.recycler.ui.items.items.categoryItem;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import myapplication.android.ui.listeners.ButtonItemListener;

public class CategoryItemModel {
    int id;
    private final String name;
    private final Task<Uri> task;
    private final int drawable;
    private final boolean isDefault;
    private final ButtonItemListener listener;

    public CategoryItemModel(int id, String name, Task<Uri> task, int drawable, boolean isDefault, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.drawable = drawable;
        this.task = task;
        this.isDefault = isDefault;
        this.listener = listener;
    }

    public boolean compareTo(CategoryItemModel other){
        return this.hashCode() == other.hashCode();
    }

    public int getDrawable() {
        return drawable;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getName() {
        return name;
    }

    public Task<Uri> getTask() {
        return task;
    }

    public ButtonItemListener getListener() {
        return listener;
    }
}
