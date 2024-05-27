package myapplication.android.ui.recycler.delegate;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainAdapter extends ListAdapter<DelegateItem, RecyclerView.ViewHolder> {

    private ArrayList<AdapterDelegate> delegates = new ArrayList<>();

    public MainAdapter() {
        super(new DelegateAdapterItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return delegates.get(viewType).onCreateViewHolder(parent);
    }

    public void addDelegate(AdapterDelegate delegate) {
        delegates.add(delegate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        delegates.get(getItemViewType(position)).onBindViewHolder(holder, getItem(position), position);
    }

    public void notifyChanged() {
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        for (int i = 0; i < delegates.size(); i++) {
            if (delegates.get(i).isOfViewType(getCurrentList().get(position))) {
                return i;
            }
        }
        return -1;
    }
}
