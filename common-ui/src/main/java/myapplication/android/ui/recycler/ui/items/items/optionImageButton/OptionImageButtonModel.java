package myapplication.android.ui.recycler.ui.items.items.optionImageButton;

import myapplication.android.ui.listeners.ButtonItemListener;

public class OptionImageButtonModel {
    int id;
    int drawable;
    String title;
    ButtonItemListener listener;

    public OptionImageButtonModel(int id, int drawable, String title, ButtonItemListener listener) {
        this.id = id;
        this.drawable = drawable;
        this.title = title;
        this.listener = listener;
    }

    public boolean compareTo(OptionImageButtonModel other){
        return other.hashCode() == this.hashCode();
    }
}
