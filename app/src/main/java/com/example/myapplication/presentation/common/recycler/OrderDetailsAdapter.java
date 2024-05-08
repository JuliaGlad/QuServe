package com.example.myapplication.presentation.common.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.RecyclerViewOrderDetailsDishBinding;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.requireChoiceCart.RequireChoiceAdapter;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.requireChoiceCart.RequireChoiceCartModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.textItem.TextItemAdapter;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.textItem.TextItemModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsAdapter extends ListAdapter<OrderDetailsItemModel, RecyclerView.ViewHolder> {

    public OrderDetailsAdapter() {
        super(new OrderDetailsItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewOrderDetailsDishBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewOrderDetailsDishBinding binding;

        public ViewHolder(RecyclerViewOrderDetailsDishBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(OrderDetailsItemModel model) {
            binding.dishName.setText(model.name);
            binding.weightCount.setText(model.weight);
            binding.dishNumber.setText(model.amount);
            binding.price.setText(model.totalPrice);

            if (model.task != null) {
                model.task.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Glide.with(itemView.getContext())
                                .load(task.getResult())
                                .into(binding.dishImage);
                    }
                });
            }

            initRequiredChoiceRecycler(model.requiredChoice);
            initToRemoveRecycler(model.toRemove);
            initToppingsRecycler(model.toppings);
        }

        private void initToRemoveRecycler(List<String> toRemove) {
            TextItemAdapter removeAdapter = new TextItemAdapter();
            List<TextItemModel> listRemoveModels = new ArrayList<>();

            for (int i = 0; i < toRemove.size(); i++) {
                listRemoveModels.add(new TextItemModel(
                        i,
                        toRemove.get(i),
                        () -> {

                        }
                ));
            }

            binding.recyclerViewToRemove.setAdapter(removeAdapter);
            removeAdapter.submitList(listRemoveModels);
        }

        private void initToppingsRecycler(List<String> topping) {
            TextItemAdapter toppingsAdapter = new TextItemAdapter();
            List<TextItemModel> toppings = new ArrayList<>();

            for (int i = 0; i < topping.size(); i++) {
                toppings.add(new TextItemModel(
                        i,
                        topping.get(i),
                        () -> {

                        }
                ));
            }
            binding.recyclerViewToppings.setAdapter(toppingsAdapter);
            toppingsAdapter.submitList(toppings);
        }

        private void initRequiredChoiceRecycler(List<String> requiredChoices) {
            RequireChoiceAdapter requireChoiceAdapter = new RequireChoiceAdapter();
            List<RequireChoiceCartModel> requireChoices = new ArrayList<>();

            for (int i = 0; i < requiredChoices.size(); i++) {
                requireChoices.add(new RequireChoiceCartModel(
                        i,
                        requiredChoices.get(i)
                ));
            }
            binding.recyclerView.setAdapter(requireChoiceAdapter);
            requireChoiceAdapter.submitList(requireChoices);
        }
    }
}
