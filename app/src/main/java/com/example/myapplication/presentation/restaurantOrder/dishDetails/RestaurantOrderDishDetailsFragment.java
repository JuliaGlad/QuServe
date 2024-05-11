package com.example.myapplication.presentation.restaurantOrder.dishDetails;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.INGREDIENT_TO_REMOVE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_PATH;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.FragmentRestaurantOrderDishDetailsBinding;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOrder.IngredientsToRemoveOrderDialogFragment;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.IngredientsToRemoveDialogFragment;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.requiredChoice.RequiredChoiceAdapter;
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
        viewModel.getDishData(restaurantId, categoryId, dishId, type);
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

    private void initOrderButton(String name, String weight, String price) {
        binding.buttonOrder.setOnClickListener(v -> {
            CartDishModel model = new CartDishModel(
                    dishId, categoryId, name, weight, price,
                    String.valueOf(1), chosenToppings, Arrays.asList(chosenRequireChoices), chosenRemove
            );

            viewModel.addToCart(restaurantId, model);
            ((RestaurantOrderDishDetailsActivity) requireActivity()).openCartActivity(restaurantId, tablePath);
        });
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
                String price = model.getPrice();

                List<VariantsModel> toppings = model.getToppings();
                List<RequiredChoiceOrderDishDetailsModel> requireChoices = model.getModels();
                List<String> toRemove = model.getIngredientsToRemove();

                chosenRequireChoices = new String[requireChoices.size()];

                binding.nameText.setText(name);
                binding.ingredientText.setText(model.getIngredients());
                binding.priceEditText.setText(price);

                Glide.with(requireView())
                        .load(model.getUri())
                        .into(binding.foodImage);

                initToppingsRecycler(toppings);
                initRequiredChoiceRecycler(requireChoices);
                initOrderButton(name, weight, price);
                initRemove(toRemove);

            } else if (state instanceof RestaurantOrderDishDetailsState.Loading) {

            } else if (state instanceof RestaurantOrderDishDetailsState.Error) {

            }
        });
    }



    private void initRemove(List<String> toRemove) {
        binding.toRemoveHeader.setOnClickListener(v -> {
            IngredientsToRemoveOrderDialogFragment dialogFragment = new IngredientsToRemoveOrderDialogFragment(toRemove, chosenRemove);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "INGREDIENTS_TO_REMOVE_ORDER_DIALOG");
            dialogFragment.OnDismissListener(bundle -> {
            });
        });
    }

    private void initToppingsRecycler(List<VariantsModel> variants) {
        if (variants != null && variants.size() > 0) {
            for (int i = 0; i < variants.size(); i++) {
                VariantsModel current = variants.get(i);
                toppingItems.add(new ToppingsOrderDelegateItem(new ToppingsOrderModel(i, current.getName(), current.getPrice(), current.getUri(), chosenToppings)));
            }
        }

        toppingsAdapter.submitList(toppingItems);
    }

    private void initRequiredChoiceRecycler(List<RequiredChoiceOrderDishDetailsModel> models) {
        if (models != null && models.size() > 0) {
            for (int i = 0; i < models.size(); i++) {
                RequiredChoiceOrderDishDetailsModel current = models.get(i);
                RequiredChoiceOrderAdapter adapter = new RequiredChoiceOrderAdapter();

                List<RequireChoiceOrderModel> choiceItemModels = new ArrayList<>();

                for (int j = 0; j < current.getVariantsName().size(); j++) {
                    String choice = current.getVariantsName().get(j);

                    boolean isDefault = false;
                    if (j == 0) {
                        chosenRequireChoices[j] = choice;
                        isDefault = true;
                    }
                    int index = i;
                    choiceItemModels.add(new RequireChoiceOrderModel(i, choice, isDefault, chosenRequireChoices[i], () -> {
                        chosenRequireChoices[index] = choice;
                    }));
                }

                requiredChoiceItems.add(new RequiredChoiceOrderHeaderDelegateItem(new RequiredChoiceOrderHeaderModel(choiceItemModels.size(), current.getName())));
                requiredChoiceItems.add(new HorizontalRecyclerDelegateItem(new HorizontalRecyclerModel(i + 1, choiceItemModels, adapter)));
            }
        }
        requiredChoiceAdapter.submitList(requiredChoiceItems);
    }

}