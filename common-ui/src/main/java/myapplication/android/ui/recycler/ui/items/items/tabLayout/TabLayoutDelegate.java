package myapplication.android.ui.recycler.ui.items.items.tabLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import myapplication.android.common_ui.databinding.RecyclerViewTabsBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class TabLayoutDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewTabsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((TabLayoutModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof TabLayoutDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewTabsBinding binding;

        public ViewHolder(RecyclerViewTabsBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(TabLayoutModel model){
//            binding.tabLayout.getTabAt(0).setText(model.firstText);
//            binding.tabLayout.getTabAt(1).setText(model.secondText);
//            binding.tabLayout.getTabAt(2).setText(model.thirdText);
//
//            binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                @Override
//                public void onTabSelected(TabLayout.Tab tab) {
//                    switch (tab.getPosition()){
//                        case 0:
//                            model.listener.onFirstTabSelected();
//                            break;
//                        case 1:
//                            model.listener.onSecondTabSelected();
//                            break;
//                        case 2:
//                            model.listener.onThirdTabSelected();
//                            break;
//                    }
//                }
//
//                @Override
//                public void onTabUnselected(TabLayout.Tab tab) {
//
//                }
//
//                @Override
//                public void onTabReselected(TabLayout.Tab tab) {
//
//                }
//            });
        }
    }
}
