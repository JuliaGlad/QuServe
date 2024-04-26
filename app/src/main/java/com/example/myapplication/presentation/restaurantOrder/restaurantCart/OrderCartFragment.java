package com.example.myapplication.presentation.restaurantOrder.restaurantCart;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_PATH;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentOrderCartBinding;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;
import com.example.myapplication.presentation.restaurantOrder.CartDishModel;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.model.OrderDishesModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.cartDishItem.CartDishItemAdapter;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.cartDishItem.CartDishItemModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.state.OrderCartState;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.state.OrderStateModel;

import java.util.ArrayList;
import java.util.List;

public class OrderCartFragment extends Fragment {

    private OrderCartViewModel viewModel;
    private FragmentOrderCartBinding binding;
    private final CartDishItemAdapter cartDishItemAdapter = new CartDishItemAdapter();
    private String restaurantId, locationId, path;
    private int totalPrice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderCartViewModel.class);
        restaurantId = getActivity().getIntent().getStringExtra(COMPANY_ID);
        path = getActivity().getIntent().getStringExtra(TABLE_PATH);
        locationId = "s";
        viewModel.getCartItems(restaurantId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setupObserves();
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof OrderCartState.Success) {
                String value = null;
                OrderStateModel stateModel = ((OrderCartState.Success) state).data;
                if (stateModel != null) {
                    List<CartDishModel> models = stateModel.getModels();
                    List<ImageTaskNameModel> uris = stateModel.getUris();
                    List<CartDishItemModel> items = new ArrayList<>();
                    for (int i = 0; i < models.size(); i++) {
                        CartDishModel current = models.get(i);
                        for (ImageTaskNameModel currentUri : uris) {
                            if (currentUri.getName().equals(current.getDishId())) {
                                value = current.getPrice().substring(current.getPrice().length()-1);
                                String price = current.getPrice().substring(0, current.getPrice().length() - 1);
                                int dishPrice = Integer.parseInt(price);
                                for (VariantCartModel cartModel : current.getToppings()) {
                                    dishPrice += Integer.parseInt(cartModel.getPrice().substring(0, current.getPrice().length() - 1));
                                }
                                totalPrice += dishPrice;
                                items.add(new CartDishItemModel(
                                        i,
                                        current.getDishId(),
                                        current.getCategoryId(),
                                        current.getName(),
                                        current.getWeight(),
                                        String.valueOf(dishPrice),
                                        current.getAmount(),
                                        currentUri.getTask(),
                                        current.getToRemove(),
                                        current.getToppings(),
                                        current.getRequiredChoices(),
                                        () -> {

                                        },
                                        () -> {
                                            viewModel.increaseAmount(current);
                                        },
                                        () -> {
                                            viewModel.decrementAmount(current);
                                        }
                                ));
                                break;
                            }
                        }
                    }
                    binding.totalPrice.setText(String.valueOf(totalPrice).concat(value));
                    cartDishItemAdapter.submitList(items);
                    initOrderButton(items);

                } else {

                }

            } else if (state instanceof OrderCartState.Loading) {

            } else if (state instanceof OrderCartState.Error) {

            }
        });

        viewModel.isOrdered.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                ((OrderCartActivity)requireActivity()).openCreatedActivity();
            }
        });
    }

    private void initOrderButton(List<CartDishItemModel> items) {
        binding.buttonOrder.setOnClickListener(v -> {
            List<OrderDishesModel> models = new ArrayList<>();
            for (CartDishItemModel current : items) {
                models.add(new OrderDishesModel(
                        current.getDishId(),
                        current.getCategoryId(),
                        current.getPrice(),
                        current.getAmount(),
                        current.getTopping(),
                        current.getToRemove(),
                        current.getRequiredChoices()
                ));
            }
            viewModel.createOrder(restaurantId, String.valueOf(totalPrice), path, models);
        });
    }

    private void setAdapter() {
        binding.recyclerView.setAdapter(cartDishItemAdapter);
    }
}