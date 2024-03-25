package com.example.myapplication.presentation.dialogFragments.chooseCity.cityRecyclerItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.RecyclerViewCityItemBinding;

public class CityItemAdapter extends ListAdapter<CityItemModel, RecyclerView.ViewHolder> {

    public CityItemAdapter() {
        super(new CityItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewCityItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewCityItemBinding binding;

        public ViewHolder(RecyclerViewCityItemBinding _binding) {
            super(_binding.getRoot());

            binding = _binding;
        }

        void bind(CityItemModel model){
            binding.cityName.setText(model.name);

            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });

        }

    }

}
