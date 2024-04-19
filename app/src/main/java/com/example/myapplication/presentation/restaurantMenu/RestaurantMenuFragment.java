package com.example.myapplication.presentation.restaurantMenu;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRestaurantMenuBinding;
import com.example.myapplication.presentation.restaurantMenu.categoryItem.CategoryItemDelegate;
import com.example.myapplication.presentation.restaurantMenu.categoryItem.CategoryItemDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.categoryItem.CategoryItemModel;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.CategoryMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.DishMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.state.RestaurantMenuState;

import java.util.ArrayList;
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
    private String restaurantId, categoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RestaurantMenuViewModel.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        viewModel.getMenuCategories(restaurantId);
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
        initAddButton();
        setGridAdapters();
        setHorizontalAdapter();
    }

    private void initAddButton() {
        binding.buttonAddDish.setOnClickListener(v -> {
            if (categoryId != null) {
                ((RestaurantMenuActivity) requireActivity()).openAddDishActivity(categoryId);
            }
        });
    }

    private void setHorizontalAdapter() {
        horizontalAdapter.addDelegate(new CategoryItemDelegate());
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

            }
        });

        viewModel.categories.observe(getActivity(), categoryMenuModels -> {
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

    private void initDishRecycler(List<DishMenuModel> models) {
        if (models.size() > 0) {
            List<DelegateItem> items = new ArrayList<>();
            for (int i = 0; i < models.size(); i++) {
                DishMenuModel current = models.get(i);
                items.add(new DishItemDelegateItem(new DishItemModel(
                        i,
                        current.getName(),
                        current.getWeight(),
                        current.getPrice(),
                        null,
                        current.getTask(),
                        () -> {
                            ((RestaurantMenuActivity)requireActivity()).openDishDetailsActivity(categoryId, current.getDishId());
                        }
                )));
            }
            gridAdapter.submitList(items);
        } else {
            final List<DelegateItem> items = new ArrayList<>();
            gridAdapter.submitList(items);
        }
    }

    private void initCategoriesRecycler(List<CategoryMenuModel> models) {
        List<DelegateItem> items = new ArrayList<>();
        if (models.size() > 0) {
            for (int i = 0; i < models.size(); i++) {
                CategoryMenuModel current = models.get(i);
                boolean isDefault = false;
                if (i == 0) {
                    isDefault = true;
                    viewModel.getCategoryDishes(restaurantId, current.getCategoryId(), true);
                    categoryId = current.getCategoryId();
                }
                items.add(new CategoryItemDelegateItem(new CategoryItemModel(
                        i,
                        current.getName(),
                        current.getTask(),
                        0,
                        isDefault,
                        () -> {
                            viewModel.getCategoryDishes(restaurantId, current.getCategoryId(), false);
                            categoryId = current.getCategoryId();
                        })));
            }
        }
        items.add(new CategoryItemDelegateItem(new CategoryItemModel(
                items.size() + 1,
                getString(R.string.add_category), null, 0, false,
                () -> {
                    ((RestaurantMenuActivity)requireActivity()).openCategoryActivity();
                }
        )));
        horizontalAdapter.submitList(items);
    }
}