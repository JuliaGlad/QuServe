package com.example.myapplication.presentation.restaurantOrder.dishDetails;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_DONE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_PATH;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRestaurantOrderDishDetailsBinding;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOrder.IngredientsToRemoveOrderDialogFragment;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.topping.ToppingDelegate;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;
import com.example.myapplication.presentation.restaurantOrder.CartDishModel;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.model.RequiredChoiceOrderDishDetailsModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.model.RestaurantOrderDishDetailsModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requireChoice.RequireChoiceOrderModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requireChoice.RequiredChoiceOrderAdapter;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requiredChoiceHeader.RequiredChoiceOrderHeaderDelegate;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requiredChoiceHeader.RequiredChoiceOrderHeaderDelegateItem;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requiredChoiceHeader.RequiredChoiceOrderHeaderModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.toppings.ToppingsOrderDelegate;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.toppings.ToppingsOrderDelegateItem;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.toppings.ToppingsOrderModel;
import com.example.myapplication.presentation.restaurantOrder.dishDetails.state.RestaurantOrderDishDetailsState;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.OrderCartActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.horizontalRecycler.HorizontalRecyclerDelegate;
import myapplication.android.ui.recycler.ui.items.items.horizontalRecycler.HorizontalRecyclerDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.horizontalRecycler.HorizontalRecyclerModel;

public class RestaurantOrderDishDetailsFragment extends Fragment {
    private RestaurantOrderDishDetailsViewModel viewModel;
    private FragmentRestaurantOrderDishDetailsBinding binding;
    private String dishId, categoryId, restaurantId, tablePath, type;
    private int totalPrice;
    private ActivityResultLauncher<Intent> cartLauncher;
    private final List<VariantCartModel> chosenToppings = new ArrayList<>();
    private final List<String> chosenRemove = new ArrayList<>();
    private String[] chosenRequireChoices;
    private final List<DelegateItem> requiredChoiceItems = new ArrayList<>();
    private final List<DelegateItem> toppingItems = new ArrayList<>();
    private final MainAdapter requiredChoiceAdapter = new MainAdapter();
    private final MainAdapter toppingsAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RestaurantOrderDishDetailsViewModel.class);
        dishId = getActivity().getIntent().getStringExtra(DISH_ID);
        tablePath = getActivity().getIntent().getStringExtra(TABLE_PATH);
        categoryId = getActivity().getIntent().getStringExtra(CATEGORY_ID);
        restaurantId = getActivity().getIntent().getStringExtra(COMPANY_ID);
        type = getActivity().getIntent().getStringExtra(STATE);
        initCartLauncher();
        viewModel.getDishData(restaurantId, categoryId, dishId, type);
    }

    private void initCartLauncher() {
        cartLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null){
                            Log.d("Is done", "exist");
                            Intent intent = new Intent();
                            intent.putExtra(IS_DONE, true);
                            requireActivity().setResult(RESULT_OK, intent);
                        }else {
                            requireActivity().finish();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantOrderDishDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToppingsAdapter();
        initRequiredChoiceAdapter();
        setupObserves();
    }

    private void initOrderButton(String name, String weight) {
        binding.buttonOrder.setOnClickListener(v -> {
            String price = String.valueOf(totalPrice);
            CartDishModel model = new CartDishModel(
                    dishId, categoryId, name, weight, price,
                    String.valueOf(1), chosenToppings, Arrays.asList(chosenRequireChoices), chosenRemove
            );
            viewModel.addToCart(restaurantId, model);
            openCartActivity();
        });
    }

    private void openCartActivity() {
        Intent intent = new Intent(requireActivity(), OrderCartActivity.class);
        intent.putExtra(COMPANY_ID, restaurantId);
        intent.putExtra(TABLE_PATH, tablePath);
        cartLauncher.launch(intent);
    }

    private void initRequiredChoiceAdapter() {
        requiredChoiceAdapter.addDelegate(new RequiredChoiceOrderHeaderDelegate());
        requiredChoiceAdapter.addDelegate(new HorizontalRecyclerDelegate());

        binding.requiredChoiceRecycler.setAdapter(requiredChoiceAdapter);
    }

    private void initToppingsAdapter() {
        toppingsAdapter.addDelegate(new ToppingsOrderDelegate());
        toppingsAdapter.addDelegate(new ToppingDelegate());

        binding.toppingsRecycler.setAdapter(toppingsAdapter);
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof RestaurantOrderDishDetailsState.Success) {
                RestaurantOrderDishDetailsModel model = ((RestaurantOrderDishDetailsState.Success) state).data;

                String name = model.getName();
                String weight = model.getWeightOrCount();
                int price = Integer.parseInt(model.getPrice());
                viewModel.setPrice(price);
                List<VariantsModel> toppings = model.getToppings();
                List<RequiredChoiceOrderDishDetailsModel> requireChoices = model.getModels();
                List<String> toRemove = model.getIngredientsToRemove();

                chosenRequireChoices = new String[requireChoices.size()];

                binding.name.setText(name);
                binding.ingredients.setText(model.getIngredients());

                Glide.with(requireView())
                        .load(model.getUri())
                        .into(binding.foodImage);

                initToppingsRecycler(toppings);
                initRequiredChoiceRecycler(requireChoices);
                initOrderButton(name, weight);
                initRemove(toRemove);
                initInfoButton(model.getEstimatedTime());
                binding.progressBar.getRoot().setVisibility(View.GONE);
                binding.errorLayout.getRoot().setVisibility(View.GONE);
            } else if (state instanceof RestaurantOrderDishDetailsState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof RestaurantOrderDishDetailsState.Error) {
                setError();
            }

            viewModel.price.observe(getViewLifecycleOwner(), price -> {
                totalPrice = price;
                binding.buttonOrder.setText(getString(R.string.add).concat(" " + "+" + totalPrice + "₽"));
            });

        });
    }

    private void initInfoButton(String time) {
        binding.buttonInfo.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.dish_details_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.getMenu().getItem(0).setTitle(time);
        });
    }

    private void setError() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getDishData(restaurantId, categoryId, dishId, type);
        });
    }

    private void initRemove(List<String> toRemove) {
        if (!toRemove.isEmpty()) {
            binding.toRemoveHeader.setOnClickListener(v -> {
                IngredientsToRemoveOrderDialogFragment dialogFragment = new IngredientsToRemoveOrderDialogFragment(toRemove, chosenRemove);
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "INGREDIENTS_TO_REMOVE_ORDER_DIALOG");
                dialogFragment.OnDismissListener(bundle -> {
                });
            });
        } else {
            binding.toRemoveHeader.setVisibility(View.GONE);
        }
    }

    private void initToppingsRecycler(List<VariantsModel> variants) {
        if (variants != null && !variants.isEmpty()) {
            for (int i = 0; i < variants.size(); i++) {
                VariantsModel current = variants.get(i);
                toppingItems.add(new ToppingsOrderDelegateItem(new ToppingsOrderModel(i, current.getName(), current.getPrice(), current.getUri(), chosenToppings,
                        () -> {
                            int price = totalPrice + Integer.parseInt(current.getPrice());
                            viewModel.setPrice(price);
                        },
                        () -> {
                            int price = totalPrice - Integer.parseInt(current.getPrice());
                            viewModel.setPrice(price);
                        })));
            }
            toppingsAdapter.submitList(toppingItems);
        } else {
            binding.toppingsHeader.setVisibility(View.GONE);
        }
    }

    private void initRequiredChoiceRecycler(List<RequiredChoiceOrderDishDetailsModel> models) {
        if (models != null && !models.isEmpty()) {
            for (int i = 0; i < models.size(); i++) {
                int indexI = i;
                RequiredChoiceOrderDishDetailsModel current = models.get(i);
                RequiredChoiceOrderAdapter adapter = new RequiredChoiceOrderAdapter();

                List<RequireChoiceOrderModel> choiceItemModels = new ArrayList<>();

                for (int j = 0; j < current.getVariantsName().size(); j++) {
                    String choice = current.getVariantsName().get(j);
                    int index = j;
                    boolean isDefault = false;
                    boolean isChosen = false;
                    if (j == 0) {
                        chosenRequireChoices[indexI] = choice;
                        isChosen = true;
                        isDefault = true;
                    }

                    choiceItemModels.add(new RequireChoiceOrderModel(i, choice, isDefault, isChosen, () -> {
                        for (int k = 0; k < choiceItemModels.size(); k++) {
                            RequireChoiceOrderModel model = choiceItemModels.get(k);
                            if (model.isChosen()) {
                                model.setChosen(false);
                                adapter.notifyItemChanged(k);
                            }
                        }
                        chosenRequireChoices[indexI] = choice;
                        RequireChoiceOrderModel model = choiceItemModels.get(index);
                        model.setChosen(true);
                        adapter.notifyItemChanged(index);
                    }));
                }
                requiredChoiceItems.add(new RequiredChoiceOrderHeaderDelegateItem(new RequiredChoiceOrderHeaderModel(choiceItemModels.size(), current.getName())));
                requiredChoiceItems.add(new HorizontalRecyclerDelegateItem(new HorizontalRecyclerModel(i + 1, choiceItemModels, adapter)));
            }
        }
        requiredChoiceAdapter.submitList(requiredChoiceItems);
    }

}