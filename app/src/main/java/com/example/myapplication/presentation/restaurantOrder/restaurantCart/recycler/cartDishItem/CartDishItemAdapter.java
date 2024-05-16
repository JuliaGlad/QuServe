package com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.cartDishItem;

import android.view.LayoutInflater;
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
            String firstPrice = models.price;
            binding.dishName.setText(models.name);
            binding.weightCount.setText(models.weight);
            binding.itemsCount.setText(models.amount);
            binding.price.setText(String.valueOf(Integer.parseInt(models.price) * Integer.parseInt(models.amount)).concat("₽"));

            if (models.task != null){
                models.task.addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Glide.with(itemView.getContext())
                                .load(task.getResult())
                                .into(binding.dishImage);
                    }
                });
            }

            initRequiredChoiceRecycler(models.requiredChoices);
            initToRemoveRecycler(models.toRemove);
            initToppingsRecycler(models.topping);

            binding.buttonAdd.setOnClickListener(v -> {
                models.addListener.onClick();
                models.amount = String.valueOf(Integer.parseInt(models.amount) + 1);
                String newPrice = String.valueOf(Integer.parseInt(models.price) + Integer.parseInt(firstPrice));
                models.price = newPrice;
                models.totalPrice = String.valueOf(Integer.parseInt(models.totalPrice) - Integer.parseInt(firstPrice) + Integer.parseInt(newPrice));
                binding.price.setText(models.price.concat("₽"));
                binding.itemsCount.setText(models.amount);
            });
            binding.buttonRemove.setOnClickListener(v -> {
                models.removeListener.onClick();
                models.amount = String.valueOf(Integer.parseInt(models.amount) - 1);
                String newPrice = String.valueOf(Integer.parseInt(models.price) - Integer.parseInt(firstPrice));
                models.price = newPrice;
                models.totalPrice = String.valueOf(Integer.parseInt(models.totalPrice) - Integer.parseInt(firstPrice) - Integer.parseInt(newPrice));
                binding.price.setText(models.price.concat("₽"));
                binding.itemsCount.setText(models.amount);
            });
        }

        private void initToRemoveRecycler(List<String> toRemove) {
            TextItemAdapter removeAdapter = new TextItemAdapter();
            List<TextItemModel> listRemoveModels  = new ArrayList<>();

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

        private void initToppingsRecycler(List<VariantCartModel> topping) {
            TextItemAdapter toppingsAdapter = new TextItemAdapter();
            List<TextItemModel> toppings = new ArrayList<>();

            for (int i = 0; i < topping.size(); i++) {
                toppings.add(new TextItemModel(
                        i,
                        topping.get(i).getName(),
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
