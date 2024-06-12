package com.example.myapplication.presentation.restaurantOrder.restaurantCart;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ORDER_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_DONE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_PATH;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentOrderCartBinding;
import com.example.myapplication.domain.model.restaurant.menu.ImageTaskNameModel;
import com.example.myapplication.presentation.restaurantOrder.CartDishModel;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.model.OrderDishesModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.cartDishItem.CartDishItemAdapter;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.recycler.cartDishItem.CartDishItemModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.state.OrderCartState;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.state.OrderStateModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class OrderCartFragment extends Fragment {

    private OrderCartViewModel viewModel;
    private FragmentOrderCartBinding binding;
    private final List<CartDishItemModel> items = new ArrayList<>();
    private final CartDishItemAdapter cartDishItemAdapter = new CartDishItemAdapter();
    private ActivityResultLauncher<Intent> launcherCreated;
    private String restaurantId, path, tableId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderCartViewModel.class);
        restaurantId = requireActivity().getIntent().getStringExtra(COMPANY_ID);
        path = requireActivity().getIntent().getStringExtra(TABLE_PATH);
        tableId = viewModel.getTableId(path);
        viewModel.getCartItems(restaurantId);
        initLauncher();
    }

    private void initLauncher() {
        launcherCreated = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            String orderId = result.getData().getStringExtra(ORDER_ID);
                            String orderPath = viewModel.getOrderPath(path, orderId);
                            Intent intent = new Intent();
                            intent.putExtra(PATH, orderPath);
                            requireActivity().setResult(RESULT_OK, intent);
                            requireActivity().finish();
                        }
                    }
                });
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
        handleBackButtonPressed();
        initButtonBack();
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            finishActivity();
        });
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishActivity();
            }
        });
    }

    private void finishActivity() {
        requireActivity().setResult(Activity.RESULT_OK);
        requireActivity().finish();
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof OrderCartState.Success) {
                OrderStateModel stateModel = ((OrderCartState.Success) state).data;
                if (stateModel != null) {
                    List<CartDishModel> models = stateModel.getModels();
                    List<ImageTaskNameModel> uris = stateModel.getUris();
                    if (!models.isEmpty()) {
                        for (int i = 0; i < models.size(); i++) {
                            CartDishModel current = models.get(i);
                            for (ImageTaskNameModel currentUri : uris) {
                                if (currentUri.getName().equals(current.getDishId())) {
                                    getDishData(currentUri, current, items, i);
                                    break;
                                }
                            }
                        }

                        binding.totalPrice.setText(String.valueOf(viewModel.price.getValue()).concat("₽"));
                        cartDishItemAdapter.submitList(items);
                        initOrderButton(items);
                    } else {
                        binding.emptyCartLayout.getRoot().setVisibility(View.VISIBLE);
                        initOpenMenuButton();
                    }
                    binding.progressBar.getRoot().setVisibility(View.GONE);
                    binding.errorLayout.getRoot().setVisibility(View.GONE);
                }

            } else if (state instanceof OrderCartState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof OrderCartState.Error) {
                setErrorLayout();
            }
        });

        viewModel.price.observe(getViewLifecycleOwner(), price -> {
            binding.totalPrice.setText(String.valueOf(price).concat("₽"));
        });

        viewModel.isOrdered.observe(getViewLifecycleOwner(), orderId -> {
            if (orderId != null) {
                ((OrderCartActivity) requireActivity()).openCreatedActivity(launcherCreated, path, orderId, restaurantId);
            }
        });
    }

    private void initOpenMenuButton() {
        binding.emptyCartLayout.buttonOpenMenu.setOnClickListener(v -> {
            finishActivity();
        });
    }

    private void setErrorLayout() {
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getCartItems(restaurantId);
        });
    }

    private void getDishData(ImageTaskNameModel currentUri, CartDishModel current, List<CartDishItemModel> items, int i) {
        String price = current.getPrice();
        int dishPrice = Integer.parseInt(price);
        String dishId = current.getDishId();
        viewModel.increasePrice(dishPrice * Integer.parseInt(current.getAmount()));
        addToCartItems(currentUri, current, items, i, dishId, dishPrice);
    }

    private void addToCartItems(ImageTaskNameModel currentUri, CartDishModel current, List<CartDishItemModel> items, int i, String dishId, int dishPrice) {
        items.add(new CartDishItemModel(
                i,
                dishId,
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
                    viewModel.increaseAmount(current);
                    viewModel.increasePrice(dishPrice);
                },
                amountNew -> {
                    if (Integer.parseInt(amountNew) == 0) {
                        viewModel.removeItemFromCart(dishId);
                        viewModel.decreasePrice(dishPrice);
                        removeFromCart(current, items, dishId);
                    } else {
                        viewModel.decrementAmount(current);
                        viewModel.decreasePrice(dishPrice);
                    }
                }
        ));
    }

    private void removeFromCart(CartDishModel current, List<CartDishItemModel> items, String dishId) {
        int toRemoveHash = current.getToRemove().hashCode();
        int requireChoiceHash = current.getRequiredChoices().hashCode();

        List<VariantCartModel> toppings = current.getToppings();
        List<String> toppingsNames = new ArrayList<>();
        for (VariantCartModel currentTopping : toppings) {
            toppingsNames.add(currentTopping.getName());
        }
        int toppingsHash = toppingsNames.hashCode();

        for (int j = 0; j < items.size(); j++) {
            CartDishItemModel dto = items.get(j);
            List<String> currentNames = new ArrayList<>();
            for (VariantCartModel currentTopping : dto.getTopping()) {
                currentNames.add(currentTopping.getName());
            }
            int hashTopping = currentNames.hashCode();
            int hashRequire = dto.getRequiredChoices().hashCode();
            int hashRemove = dto.getToRemove().hashCode();

            if (dto.getDishId().equals(dishId) && requireChoiceHash == hashRequire && toRemoveHash == hashRemove && toppingsHash == hashTopping) {
                items.remove(j);
                cartDishItemAdapter.notifyItemRemoved(j);
                if (cartDishItemAdapter.getCurrentList().isEmpty()){
                    binding.emptyCartLayout.getRoot().setVisibility(View.VISIBLE);
                    initOpenMenuButton();
                }
                break;
            }
        }
    }

    private void initOrderButton(List<CartDishItemModel> items) {
        binding.buttonOrder.setOnClickListener(v -> {
            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonOrder.setEnabled(false);
            if (!items.isEmpty()) {
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
                viewModel.createOrder(restaurantId, tableId, String.valueOf(viewModel.price.getValue()), path, models);
            } else {
                Snackbar.make(requireView(), getString(R.string.you_have_to_add_at_least_1_dish_to_create_order), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setAdapter() {
        binding.recyclerView.setAdapter(cartDishItemAdapter);
    }
}