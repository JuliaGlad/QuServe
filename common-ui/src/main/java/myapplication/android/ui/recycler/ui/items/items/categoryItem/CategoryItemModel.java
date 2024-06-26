package myapplication.android.ui.recycler.ui.items.items.categoryItem;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import java.util.Objects;

import myapplication.android.ui.listeners.ButtonBooleanItemListener;
import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.ButtonObjectListener;

public class CategoryItemModel {
    public int id;
    public final String categoryId;
    public final String name;
    public final Task<Uri> task;
    public final Uri uri;
    public final int drawable;
    public final boolean isDefault;
    public boolean isChosen;
    public boolean isEditable;
    public final ButtonObjectListener listenerDelete;
    public final ButtonItemListener listener;

    public CategoryItemModel(int id, String categoryId, String name, Task<Uri> task, Uri uri, int drawable, boolean isDefault, boolean isChosen, boolean isEditable, ButtonObjectListener listenerDelete, ButtonItemListener listener) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.categoryId = categoryId;
        this.drawable = drawable;
        this.task = task;
        this.isEditable = isEditable;
        this.isDefault = isDefault;
        this.listener = listener;
        this.listenerDelete = listenerDelete;
        this.isChosen = isChosen;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public boolean compareTo(CategoryItemModel other) {
        return this.isChosen == other.isChosen();
    }

    public Uri getUri() {
        return uri;
    }

    public int getDrawable() {
        return drawable;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CategoryItemModel)) return false;
        CategoryItemModel that = (CategoryItemModel) o;
        return id == that.id && drawable == that.drawable && isDefault == that.isDefault && isChosen == that.isChosen && Objects.equals(name, that.name) && Objects.equals(task, that.task) && Objects.equals(uri, that.uri) && Objects.equals(listener, that.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, task, uri, drawable, isDefault, isChosen, listener);
    }
}
