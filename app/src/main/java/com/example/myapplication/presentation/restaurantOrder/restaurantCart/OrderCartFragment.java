package com.example.myapplication.presentation.restaurantOrder.restaurantCart;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_PATH;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentOrderCartBinding;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
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
    private String restaurantId, path;
    private int totalPrice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderCartViewModel.class);
        restaurantId = getActivity().getIntent().getStringExtra(COMPANY_ID);
        path = getActivity().getIntent().getStringExtra(TABLE_PATH);
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
                OrderStateModel stateModel = ((OrderCartState.Success) state).data;
                if (stateModel != null) {
                    List<CartDishModel> models = stateModel.getModels();
                    List<ImageTaskNameModel> uris = stateModel.getUris();
                    List<CartDishItemModel> items = new ArrayList<>();
                    for (int i = 0; i < models.size(); i++) {
                        CartDishModel current = models.get(i);
                        for (ImageTaskNameModel currentUri : uris) {
                            if (currentUri.getName().equals(current.getDishId())) {
                                String price = current.getPrice();
                                int dishPrice = Integer.parseInt(price);
                                for (VariantCartModel cartModel : current.getToppings()) {
                                    dishPrice += Integer.parseInt(cartModel.getPrice());
                                }
                                String amount = current.getAmount();
                                String dishId = current.getDishId();
                                totalPrice += (dishPrice * Integer.parseInt(current.getAmount()));
                                items.add(new CartDishItemModel(
                                        i,
                                        dishId,
                                        current.getCategoryId(),
                                        current.getName(),
                                        current.getWeight(),
                                        String.valueOf(dishPrice),
                                        String.valueOf(totalPrice),
                                        amount,
                                        currentUri.getTask(),
                                        current.getToRemove(),
                                        current.getToppings(),
                                        current.getRequiredChoices(),
                                        () -> {
                                            viewModel.increaseAmount(current);
                                        },
                                        () -> {
                                            if (Integer.parseInt(amount) == 1){
                                                viewModel.removeItemFromCart(dishId);
                                                binding.totalPrice.setText(String.valueOf(totalPrice));
                                            } else {
                                                viewModel.decrementAmount(current);
                                                binding.totalPrice.setText(String.valueOf(totalPrice));
                                            }
                                        }
                                ));
                                break;
                            }
                        }
                    }
                    binding.totalPrice.setText(String.valueOf(totalPrice));
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
                        current.getName(),
                        current.getPrice(),
                        current.getAmount(),
                        current.getWeight(),
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