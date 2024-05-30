package com.example.myapplication.presentation.restaurantOrder.menu;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_DATA;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentRestaurantMenuOrderBinding;
import com.example.myapplication.presentation.dialogFragments.tableAlreadyHaveOrder.TableAlreadyHaveOrderDialogFragment;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.CategoryMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.DishMenuModel;
import com.example.myapplication.presentation.restaurantOrder.menu.recycler.DishOrderItemAdapter;
import com.example.myapplication.presentation.restaurantOrder.menu.recycler.DishOrderModel;
import com.example.myapplication.presentation.restaurantOrder.menu.state.RestaurantMenuOrderState;

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
    private final List<DelegateItem> categories = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;
    private final List<DishOrderModel> dishes = new ArrayList<>();
    private String categoryId, tablePath, restaurantId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RestaurantMenuOrderViewModel.class);
        tablePath = requireActivity().getIntent().getStringExtra(RESTAURANT_DATA);
        restaurantId = viewModel.getRestaurantId(tablePath);
        viewModel.checkTableOrder(tablePath);

        initLauncher();
    }

    private void initLauncher() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            requireActivity().setResult(RESULT_OK);
                            requireActivity().finish();
                        }
                    }
                });
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
            ((RestaurantOrderMenuActivity) requireActivity()).openCartActivity(restaurantId, tablePath, launcher);
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

        viewModel.isChecked.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null){
                if (!aBoolean){
                    viewModel.getMenuCategories(restaurantId);
                } else {
                    TableAlreadyHaveOrderDialogFragment dialogFragment = new TableAlreadyHaveOrderDialogFragment();
                    dialogFragment.show(requireActivity().getSupportFragmentManager(), "TABLE_ALREADY_HAVE_ORDER");
                    dialogFragment.onDismissListener(bundle -> {
                        requireActivity().finish();
                    });
                }
            }
        });

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof RestaurantMenuOrderState.Success) {
                List<DishMenuModel> models = ((RestaurantMenuOrderState.Success) state).data;
                if (!categories.isEmpty()) {
                    if (!models.isEmpty()) {
                        initDishRecycler(models, true);
                    } else {
                        binding.constraintLayoutEmptyDishes.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.constraintLayoutEmptyCategories.setVisibility(View.VISIBLE);
                }
                binding.progressLayout.getRoot().setVisibility(View.GONE);
                binding.errorLayout.getRoot().setVisibility(View.GONE);
            } else if (state instanceof RestaurantMenuOrderState.Loading) {
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof RestaurantMenuOrderState.Error) {
                setErrorLayout();
            }
        });

        viewModel.categories.observe(requireActivity(), categoryMenuModels -> {
            if (categoryMenuModels != null) {
                initCategoriesRecycler(categoryMenuModels);
            }
        });

        viewModel.newCategory.observe(getViewLifecycleOwner(), dishMenuModels -> {
            if (dishMenuModels != null) {
                if (!dishMenuModels.isEmpty()) {
                    binding.constraintLayoutEmptyDishes.setVisibility(View.GONE);
                    boolean isDefault = false;
                    if (dishes.isEmpty()){
                        isDefault = true;
                    }
                    initDishRecycler(dishMenuModels, isDefault);
                } else {
                    int size = dishes.size();
                    dishes.clear();
                    gridAdapter.notifyItemRangeRemoved(0, size);
                    binding.constraintLayoutEmptyDishes.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setErrorLayout() {
        binding.errorLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getMenuCategories(restaurantId);
        });
    }

    private void initDishRecycler(List<DishMenuModel> models, boolean isDefault) {
        int size = dishes.size();
        dishes.clear();
        gridAdapter.notifyItemRangeRemoved(0, size);
        if (!models.isEmpty()) {
            for (int i = 0; i < models.size(); i++) {
                DishMenuModel current = models.get(i);
                dishes.add(new DishOrderModel(
                        i,
                        current.getName(),
                        current.getWeight(),
                        current.getPrice(),
                        null,
                        current.getTask(),
                        () -> {
                            ((RestaurantOrderMenuActivity) requireActivity())
                                    .openRestaurantOrderDishDetailsActivity(restaurantId, tablePath, categoryId, current.getDishId(), launcher);
                        }
                ));
            }
            if (!isDefault) {
                gridAdapter.notifyItemRangeInserted(0, models.size());
            } else {
                gridAdapter.submitList(dishes);
            }
        } else {
            gridAdapter.submitList(dishes);
        }
    }

    private void initCategoriesRecycler(List<CategoryMenuModel> models) {
        if (!models.isEmpty()) {
            for (int i = 0; i < models.size(); i++) {
                int index = i;
                CategoryMenuModel current = models.get(i);
                boolean isDefault = false;
                boolean isChosen = false;
                if (index == 0) {
                    isDefault = true;
                    isChosen = true;
                    viewModel.getCategoryDishes(restaurantId, current.getCategoryId(), true);
                    categoryId = current.getCategoryId();
                }

                categories.add(new CategoryItemDelegateItem(new CategoryItemModel(
                        index,
                        current.getCategoryId(),
                        current.getName(),
                        current.getTask(),
                        Uri.EMPTY,
                        0,
                        isDefault,
                        isChosen,
                        false,
                        null,
                        () -> {
                            for (int j = 0; j < categories.size(); j++) {
                                if (categories.get(j) instanceof CategoryItemDelegateItem) {
                                    CategoryItemModel model = (CategoryItemModel) categories.get(j).content();
                                    if (model.isChosen) {
                                        model.setChosen(false);
                                        horizontalAdapter.notifyItemChanged(j);
                                    }
                                }
                            }

                            CategoryItemModel model = (CategoryItemModel) categories.get(index).content();
                            model.setChosen(true);
                            horizontalAdapter.notifyItemChanged(index);

                            viewModel.getCategoryDishes(restaurantId, current.getCategoryId(), false);
                            categoryId = current.getCategoryId();
                        })));
            }
        }
        horizontalAdapter.submitList(categories);
    }
}