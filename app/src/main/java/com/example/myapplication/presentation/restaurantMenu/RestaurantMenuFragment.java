package com.example.myapplication.presentation.restaurantMenu;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.URI;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_WEIGHT_OR_COUNT;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentRestaurantMenuBinding;

import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemDelegate;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemModel;

import com.example.myapplication.presentation.restaurantMenu.AddCategory.AddMenuCategoryActivity;
import com.example.myapplication.presentation.restaurantMenu.addDish.AddDishActivity;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping.AddToppingDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping.AddToppingDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping.AddToppingModel;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.CategoryMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.DishMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.state.RestaurantMenuState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.imageDrawable.ImageViewDrawableDelegate;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class RestaurantMenuFragment extends Fragment {

    private RestaurantMenuViewModel viewModel;
    private FragmentRestaurantMenuBinding binding;
    private final MainAdapter horizontalAdapter = new MainAdapter();
    private final MainAdapter gridAdapter = new MainAdapter();
    private ActivityResultLauncher<Intent> addCategoryLauncher, addDishLauncher;
    private final List<DelegateItem> categories = new ArrayList<>();
    List<DelegateItem> dishes = new ArrayList<>();
    private String restaurantId, categoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RestaurantMenuViewModel.class);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        viewModel.getMenuCategories(restaurantId);
        initCategoryLauncher();
        initDishLauncher();
    }

    private void initDishLauncher() {
        addDishLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String dishId = result.getData().getStringExtra(DISH_ID);
                        String dishName = result.getData().getStringExtra(DISH_NAME);
                        String dishPrice = result.getData().getStringExtra(DISH_PRICE);
                        String dishWeight = result.getData().getStringExtra(DISH_WEIGHT_OR_COUNT);
                        Uri uri = Uri.parse(result.getData().getStringExtra(URI));
                        int position = dishes.size();
                        dishes.add(new DishItemDelegateItem(new DishItemModel(
                                position, dishName, dishWeight, dishPrice, uri, null,
                                () -> ((RestaurantMenuActivity) requireActivity()).openDishDetailsActivity(categoryId, dishId)
                        )));
                        gridAdapter.notifyItemInserted(position);
                    }
                });
    }

    private void initCategoryLauncher() {
        addCategoryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String categoryId = result.getData().getStringExtra(COMPANY_ID);
                        String name = result.getData().getStringExtra(COMPANY_NAME);
                        Uri uri = Uri.parse(result.getData().getStringExtra(URI));
                        int position = categories.size() - 1;
                        categories.remove(position);
                        horizontalAdapter.notifyItemRemoved(position);
                        categories.add(new CategoryItemDelegateItem(new CategoryItemModel(
                                position, name, null, uri, 0, false, () -> {
                            viewModel.getCategoryDishes(restaurantId, categoryId, false);
                            this.categoryId = categoryId;
                        }
                        )));
                        horizontalAdapter.notifyItemInserted(position);
                        categories.add(new AddToppingDelegateItem(new AddToppingModel(categories.size(), this::launchAddCategory)));
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        if (viewModel.checkUserID()) {
            viewModel.signInAnonymously();
        } else {
            initUi();
        }
    }

    private void initUi() {
        initAddButton();
        setGridAdapters();
        setHorizontalAdapter();
    }

    private void initAddButton() {
        binding.buttonAddDish.setOnClickListener(v -> {
            if (categoryId != null) {
                openAddDishActivity(categoryId);
            }
        });
    }

    private void setHorizontalAdapter() {
        horizontalAdapter.addDelegate(new CategoryItemDelegate());
        horizontalAdapter.addDelegate(new AddToppingDelegate());
        binding.categoryRecyclerView.setAdapter(horizontalAdapter);
    }

    private void setGridAdapters() {
        gridAdapter.addDelegate(new ImageViewDrawableDelegate());
        gridAdapter.addDelegate(new DishItemDelegate());
        gridAdapter.addDelegate(new StringTextViewDelegate());

        binding.dishesRecyclerView.setAdapter(gridAdapter);
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof RestaurantMenuState.Success) {
                List<DishMenuModel> models = ((RestaurantMenuState.Success) state).data;
                initDishRecycler(models);
            } else if (state instanceof RestaurantMenuState.Loading) {

            } else if (state instanceof RestaurantMenuState.Error) {
                setErrorLayout();
            }
        });

        viewModel.isSignIn.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                initUi();
            }
        });

        viewModel.categories.observe(getViewLifecycleOwner(), categoryMenuModels -> {
            if (categoryMenuModels != null) {
                initCategoriesRecycler(categoryMenuModels);
            }
        });

        viewModel.newCategory.observe(getViewLifecycleOwner(), dishMenuModels -> {
            if (dishMenuModels != null) {
                initDishRecycler(dishMenuModels);
            }
        });
    }

    private void setErrorLayout() {
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> viewModel.getMenuCategories(restaurantId));
    }

    private void initDishRecycler(List<DishMenuModel> models) {
        if (!models.isEmpty()) {
            for (int i = 0; i < models.size(); i++) {
                DishMenuModel current = models.get(i);
                dishes.add(new DishItemDelegateItem(new DishItemModel(
                        i,
                        current.getName(),
                        current.getWeight(),
                        current.getPrice(),
                        Uri.EMPTY,
                        current.getTask(),
                        () -> ((RestaurantMenuActivity) requireActivity()).openDishDetailsActivity(categoryId, current.getDishId())
                )));
            }
            gridAdapter.submitList(dishes);
        } else {
            final List<DelegateItem> items = new ArrayList<>();
            gridAdapter.submitList(items);
        }
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    private void initCategoriesRecycler(List<CategoryMenuModel> models) {
        if (!models.isEmpty()) {
            for (int i = 0; i < models.size(); i++) {
                CategoryMenuModel current = models.get(i);
                boolean isDefault = false;
                if (i == models.size() - 1) {
                    isDefault = true;
                    viewModel.getCategoryDishes(restaurantId, current.getCategoryId(), true);
                    categoryId = current.getCategoryId();
                }
                categories.add(new CategoryItemDelegateItem(new CategoryItemModel(
                        i,
                        current.getName(),
                        current.getTask(),
                        Uri.EMPTY,
                        0,
                        isDefault,
                        () -> {
                            int size = dishes.size();
                            dishes.clear();
                            gridAdapter.notifyItemRangeRemoved(0, size);
                            viewModel.getCategoryDishes(restaurantId, current.getCategoryId(), false);
                            categoryId = current.getCategoryId();
                        })));
            }
        }
        categories.add(new AddToppingDelegateItem(new AddToppingModel(
                categories.size() + 1,
                this::launchAddCategory
        )));
        horizontalAdapter.submitList(categories);
    }

    private void launchAddCategory() {
        Intent intent = new Intent(requireActivity(), AddMenuCategoryActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        addCategoryLauncher.launch(intent);
    }

    public void openAddDishActivity(String categoryId) {
        Intent intent = new Intent(requireActivity(), AddDishActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(CATEGORY_ID, categoryId);
        addDishLauncher.launch(intent);
    }
}