package com.example.myapplication.presentation.restaurantOrder.menu;

import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_DATA;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRestaurantMenuOrderBinding;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.CategoryMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.DishMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.state.RestaurantMenuState;
import com.example.myapplication.presentation.restaurantMenu.RestaurantMenuActivity;
import com.example.myapplication.presentation.restaurantOrder.menu.recycler.DishOrderItemAdapter;
import com.example.myapplication.presentation.restaurantOrder.menu.recycler.DishOrderModel;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemDelegate;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemModel;

public class RestaurantMenuOrderFragment extends Fragment {

    private RestaurantMenuOrderViewModel viewModel;
    private FragmentRestaurantMenuOrderBinding binding;
    private final MainAdapter horizontalAdapter = new MainAdapter();
    private final DishOrderItemAdapter gridAdapter = new DishOrderItemAdapter();
    private String restaurantId, categoryId, tablePath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RestaurantMenuOrderViewModel.class);
        tablePath = getActivity().getIntent().getStringExtra(RESTAURANT_DATA);
        restaurantId = tablePath.substring(15, 35);
        viewModel.getMenuCategories(restaurantId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantMenuOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initButtonBack();
        initButtonCart();
        setGridAdapters();
        setHorizontalAdapter();
    }

    private void initButtonCart() {
        binding.buttonCart.setOnClickListener(v -> {
            ((RestaurantOrderMenuActivity)requireActivity()).openCartActivity(restaurantId, tablePath);
        });
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setHorizontalAdapter() {
        horizontalAdapter.addDelegate(new CategoryItemDelegate());
        binding.categoryRecyclerView.setAdapter(horizontalAdapter);
    }

    private void setGridAdapters() {
        binding.dishesRecyclerView.setAdapter(gridAdapter);
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof RestaurantMenuState.Success) {
                List<DishMenuModel> models = ((RestaurantMenuState.Success) state).data;
                initDishRecycler(models);
                binding.errorLayout.getRoot().setVisibility(View.GONE);
            } else if (state instanceof RestaurantMenuState.Loading) {

            } else if (state instanceof RestaurantMenuState.Error) {
                setErrorLayout();
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

    private void setErrorLayout() {
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getMenuCategories(restaurantId);
        });
    }

    private void initDishRecycler(List<DishMenuModel> models) {
        List<DishOrderModel> items = new ArrayList<>();
        if (!models.isEmpty()) {
            for (int i = 0; i < models.size(); i++) {
                DishMenuModel current = models.get(i);
                items.add(new DishOrderModel(
                        i,
                        current.getName(),
                        current.getWeight(),
                        current.getPrice(),
                        null,
                        current.getTask(),
                        () -> {
                            ((RestaurantOrderMenuActivity)requireActivity()).openRestaurantOrderDishDetailsActivity(restaurantId, tablePath, categoryId, current.getDishId());
                        }
                ));
            }
            gridAdapter.submitList(items);
        } else {
            gridAdapter.submitList(items);
        }
    }

    private void initCategoriesRecycler(List<CategoryMenuModel> models) {
        List<DelegateItem> items = new ArrayList<>();
        if (!models.isEmpty()) {
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
        horizontalAdapter.submitList(items);
    }
}