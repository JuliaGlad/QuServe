package myapplication.android.ui.recycler.ui.items.items.horizontalRecycler;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HorizontalRecyclerModel {
    int id;
    ListAdapter adapter;
    List items;

    public HorizontalRecyclerModel(int id, List items, ListAdapter adapter) {
        this.id = id;
        this.adapter = adapter;
        this.items = items;
    }

    public ListAdapter<Object, RecyclerView.ViewHolder> getAdapter() {
        return adapter;
    }

    public List getItems() {
        return items;
    }

    public int getId() {
        return id;
    }
}
