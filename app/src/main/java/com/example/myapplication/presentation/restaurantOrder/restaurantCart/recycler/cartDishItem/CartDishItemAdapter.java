package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.cartDishItem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.RecyclerViewCartItemBinding;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.requireChoiceCart.RequireChoiceAdapter;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.requireChoiceCart.RequireChoiceCartModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.textItem.TextItemAdapter;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.textItem.TextItemModel;

import java.util.ArrayList;
import java.util.List;

public class CartDishItemAdapter extends ListAdapter<CartDishItemModel, RecyclerView.ViewHolder> {

    public CartDishItemAdapter() {
        super(new CartDishItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewCartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewCartItemBinding binding;

        public ViewHolder(RecyclerViewCartItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(CartDishItemModel models) {
            binding.loader.setVisibility(View.VISIBLE);
            String firstDishPrice = models.price;
            binding.dishName.setText(models.name);
            binding.weightCount.setText(models.weight);
            binding.layoutAddWidget.itemsCount.setText(models.amount);
            binding.layoutAddWidget.price.setText(String.valueOf(Integer.parseInt(models.price) * Integer.parseInt(models.amount)).concat("₽"));

            if (models.task != null){
                models.task.addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Glide.with(itemView.getContext())
                                .load(task.getResult())
                                .into(binding.dishImage);
                        binding.loader.setVisibility(View.GONE);
                    }
                });
            }

            initRequiredChoiceRecycler(models.requiredChoices);
            initToRemoveRecycler(models.toRemove);
            initToppingsRecycler(models.topping);

            binding.layoutAddWidget.buttonAdd.setOnClickListener(v -> {
                int currentTotal = Integer.parseInt(firstDishPrice) * Integer.parseInt(models.amount);
                models.price = String.valueOf(currentTotal + Integer.parseInt(firstDishPrice));
                models.amount = String.valueOf(Integer.parseInt(models.amount) + 1);
                binding.layoutAddWidget.price.setText(models.price.concat("₽"));
                binding.layoutAddWidget.itemsCount.setText(models.amount);
                models.addListener.onClick();
            });
            binding.layoutAddWidget.buttonRemove.setOnClickListener(v -> {
                int currentTotal = Integer.parseInt(firstDishPrice) * Integer.parseInt(models.amount);
                models.price = String.valueOf(currentTotal - Integer.parseInt(firstDishPrice));
                models.amount = String.valueOf(Integer.parseInt(models.amount) - 1);
                binding.layoutAddWidget.price.setText(models.price.concat("₽"));
                binding.layoutAddWidget.itemsCount.setText(models.amount);
                models.removeListener.onClick(models.amount);
            });
        }

        private void initToRemoveRecycler(List<String> toRemove) {
            TextItemAdapter removeAdapter = new TextItemAdapter();
            List<TextItemModel> listRemoveModels  = new ArrayList<>();

            if (!toRemove.isEmpty()) {
                for (int i = 0; i < toRemove.size(); i++) {
                    listRemoveModels.add(new TextItemModel(
                            i,
                            toRemove.get(i),
                            () -> {

                            }
                    ));
                }
            } else {
                binding.removeItemIcon.setVisibility(View.GONE);
            }

            binding.recyclerViewToRemove.setAdapter(removeAdapter);
            removeAdapter.submitList(listRemoveModels);
        }

        private void initToppingsRecycler(List<VariantCartModel> topping) {
            TextItemAdapter toppingsAdapter = new TextItemAdapter();
            List<TextItemModel> toppings = new ArrayList<>();

            if (!topping.isEmpty()) {
                for (int i = 0; i < topping.size(); i++) {
                    toppings.add(new TextItemModel(
                            i,
                            topping.get(i).getName(),
                            () -> {

                            }
                    ));
                }
            } else {
                binding.addItemIcon.setVisibility(View.GONE);
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
