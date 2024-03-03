package myapplication.android.ui.recycler.ui.items.items.tabLayout;

import myapplication.android.ui.listeners.TabSelectedListener;

public class TabLayoutModel {
    int id;
    int firstText;
    int secondText;
    int thirdText;
    TabSelectedListener listener;

    public TabLayoutModel(int id, int firstText, int secondText, int thirdText, TabSelectedListener listener ) {
        this.id = id;
        this.firstText = firstText;
        this.secondText = secondText;
        this.thirdText = thirdText;
        this.listener = listener;
    }

    public int getId() {
        return id;
    }

}
