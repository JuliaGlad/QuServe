package com.example.myapplication.presentation.restaurantMenu;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.constants.Utils.IMAGE;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.POSITION;
import static com.example.myapplication.presentation.utils.constants.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.UPDATED;
import static com.example.myapplication.presentation.utils.constants.Utils.URI;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_WEIGHT_OR_COUNT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRestaurantMenuBinding;
import com.example.myapplication.presentation.companyQueue.queueManager.recycler_view.ManagerItemModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.AddMenuCategoryActivity;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.CategoryMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.DishMenuModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.state.RestaurantMenuState;
import com.example.myapplication.presentation.restaurantMenu.addDish.AddDishActivity;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping.AddToppingDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping.AddToppingDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping.AddToppingModel;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemModel;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemDelegate;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemModel;
import myapplication.android.ui.recycler.ui.items.items.imageDrawable.ImageViewDrawableDelegate;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class RestaurantMenuFragment extends Fragment {

    private RestaurantMenuViewModel viewModel;
    private FragmentRestaurantMenuBinding binding;
    private final MainAdapter horizontalAdapter = new MainAdapter();
    private final MainAdapter gridAdapter = new MainAdapter();
    private ActivityResultLauncher<Intent> addCategoryLauncher, addDishLauncher, updateDishLauncher;
    private final List<DelegateItem> categories = new ArrayList<>();
    List<DelegateItem> dishes = new ArrayList<>();
    private String restaurantId, categoryId;
    private boolean isDefaultDishes = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RestaurantMenuViewModel.class);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        viewModel.getMenuCategories(restaurantId);
        initCategoryLauncher();
        initDishLauncher();
        initUpdateLauncher();
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
        initBackButton();
        initSearchView();
    }

    private void initUpdateLauncher() {
        updateDishLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        int position = result.getData().getIntExtra(POSITION, 0);
                        String state = result.getData().getStringExtra(STATE);
                        String dishId = result.getData().getStringExtra(DISH_ID);
                        String dishName = result.getData().getStringExtra(DISH_NAME);
                        String dishPrice = result.getData().getStringExtra(DISH_PRICE);
                        String dishWeight = result.getData().getStringExtra(DISH_WEIGHT_OR_COUNT);
                        Uri uri = Uri.parse(result.getData().getStringExtra(URI));
                        dishes.remove(position);
                        gridAdapter.notifyItemRemoved(position);
                        assert state != null;
                        if (state.equals(UPDATED)) {
                            dishes.add(new DishItemDelegateItem(new DishItemModel(
                                    position, dishId, dishName, dishWeight, dishPrice, uri, null, false,
                                    () -> updateDish(dishId)
                            )));
                            gridAdapter.notifyItemInserted(dishes.size() - 1);
                        }
                    }
                });
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
                                position, dishId, dishName, dishWeight, dishPrice, uri, null, false,
                                () -> updateDish(dishId)
                        )));

                        if (isDefaultDishes) {
                            binding.constraintLayoutEmptyDishes.setVisibility(View.GONE);
                            gridAdapter.submitList(dishes);
                        } else {
                            binding.constraintLayoutEmptyDishes.setVisibility(View.GONE);
                            gridAdapter.notifyItemInserted(position);
                        }
                    }
                });
    }

    private void initCategoryLauncher() {
        addCategoryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        binding.constraintLayoutEmptyCategories.setVisibility(View.GONE);
                        binding.buttonAddDish.setVisibility(View.VISIBLE);

                        String categoryId = result.getData().getStringExtra(COMPANY_ID);
                        String name = result.getData().getStringExtra(COMPANY_NAME);
                        String image = null;
                        Uri uri = Uri.EMPTY;
                        try {
                            uri = Uri.parse(result.getData().getStringExtra(URI));
                        } catch (NullPointerException e) {
                            image = result.getData().getStringExtra(IMAGE);
                        }
                        int position = categories.size() - 1;

                        categories.remove(position);
                        horizontalAdapter.notifyItemRemoved(position);

                        int size = dishes.size();
                        dishes.clear();
                        gridAdapter.notifyItemRangeRemoved(0, size);
                        this.categoryId = categoryId;
                        binding.constraintLayoutEmptyDishes.setVisibility(View.VISIBLE);

                        Bundle bundle = new Bundle();
                        bundle.putString(COMPANY_NAME, name);
                        bundle.putInt(POSITION, position);
                        bundle.putString(COMPANY_ID, categoryId);

                        viewModel.getImageDrawable(bundle, image, uri);
                    }
                });
    }

    private void changeCategory(String categoryId) {
        int position = 0;
        for (int j = 0; j < categories.size(); j++) {
            if (categories.get(j) instanceof CategoryItemDelegateItem) {
                CategoryItemModel model = (CategoryItemModel) categories.get(j).content();
                if (model.isChosen()) {
                    model.setChosen(false);
                    horizontalAdapter.notifyItemChanged(j);
                }
                if (model.getCategoryId().equals(categoryId)) {
                    position = j;
                }
            }
        }

        CategoryItemModel model = (CategoryItemModel) categories.get(position).content();
        model.setChosen(true);
        horizontalAdapter.notifyItemChanged(position);

        int size = dishes.size();
        dishes.clear();
        gridAdapter.notifyItemRangeRemoved(0, size);
        this.categoryId = categoryId;
        viewModel.getCategoryDishes(restaurantId, categoryId, false);
    }

    private void initSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String key) {
        if (!key.isEmpty()) {
            List<DelegateItem> modelList = gridAdapter.getCurrentList();
            List<DelegateItem> filteredList = new ArrayList<>();
            for (int i = 0; i < modelList.size(); i++) {
                if (modelList.get(i) instanceof DishItemDelegateItem) {
                    DishItemModel dish = ((DishItemDelegateItem) modelList.get(i)).content();
                    if (dish.getName().toLowerCase().contains(key.toLowerCase())) {
                        filteredList.add(modelList.get(i));
                    }
                }
            }
            gridAdapter.submitList(filteredList);
        } else {
            gridAdapter.submitList(dishes);
        }
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
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
                if (!models.isEmpty()) {
                    initDishRecycler(models);
                } else if (categories.size() > 1) {
                    binding.progressBar.getRoot().setVisibility(View.GONE);
                    binding.constraintLayoutEmptyDishes.setVisibility(View.VISIBLE);
                }
            } else if (state instanceof RestaurantMenuState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof RestaurantMenuState.Error) {
                setErrorLayout();
            }
        });

        viewModel.addedUri.observe(getViewLifecycleOwner(), bundle -> {
            if (bundle != null) {
                int position = bundle.getInt(POSITION);
                String name = bundle.getString(COMPANY_NAME);
                String categoryId = bundle.getString(COMPANY_ID);
                Uri uri = Uri.parse(bundle.getString(URI));

                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i) instanceof CategoryItemDelegateItem) {
                        CategoryItemModel current = ((CategoryItemDelegateItem) categories.get(i)).content();
                        current.setChosen(false);
                        horizontalAdapter.notifyItemChanged(i);
                    }
                }

                categories.add(new CategoryItemDelegateItem(new CategoryItemModel(
                        position, categoryId, name, null, uri, 0, false, true, true,
                        view -> {
                            PopupMenu popupMenu = new PopupMenu(requireContext(), (View) view);
                            popupMenu.getMenuInflater().inflate(R.menu.delete_category_menu, popupMenu.getMenu());
                            popupMenu.show();

                            popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
                                int positionCurrent = 0;
                                for (int j = 0; j < categories.size(); j++) {
                                    if (categories.get(j) instanceof CategoryItemDelegateItem) {
                                        CategoryItemModel currentModel = ((CategoryItemDelegateItem) categories.get(j)).content();
                                        if (currentModel.getCategoryId().equals(categoryId)) {
                                            positionCurrent = j;
                                        }
                                    }
                                }
                                viewModel.deleteCategory(positionCurrent, restaurantId, categoryId);
                                return true;
                            });
                        },
                        () -> changeCategory(categoryId)
                )));
                horizontalAdapter.notifyItemInserted(position);
                categories.add(new AddToppingDelegateItem(new AddToppingModel(categories.size(), this::launchAddCategory)));
            }
        });

        viewModel.categoryDeleted.observe(getViewLifecycleOwner(), position -> {
            if (position != null) {
                categories.remove(position.intValue());
                horizontalAdapter.notifyItemRemoved(position);
                if (!categories.isEmpty()) {
                    if (categories.get(0) instanceof CategoryItemDelegateItem) {
                        CategoryItemModel model = (CategoryItemModel) categories.get(0).content();
                        model.setChosen(true);
                        horizontalAdapter.notifyItemChanged(position);

                        int size = dishes.size();
                        dishes.clear();
                        gridAdapter.notifyItemRangeRemoved(0, size);
                        categoryId = model.getCategoryId();
                        viewModel.getCategoryDishes(restaurantId, categoryId, false);
                    }
                }
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
                if (!dishMenuModels.isEmpty()) {
                    binding.constraintLayoutEmptyDishes.setVisibility(View.GONE);
                    initDishRecycler(dishMenuModels);
                } else {
                    binding.constraintLayoutEmptyDishes.setVisibility(View.VISIBLE);
                }
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
                        current.getDishId(),
                        current.getName(),
                        current.getWeight(),
                        current.getPrice(),
                        Uri.EMPTY,
                        current.getTask(),
                        false,
                        () -> updateDish(current.getDishId())
                )));
            }
            if (!isDefaultDishes) {
                if (!dishes.isEmpty()) {
                    if (dishes.size() > 1) {
                        gridAdapter.notifyItemRangeInserted(0, dishes.size() - 1);
                    } else {
                        gridAdapter.notifyItemInserted(0);
                    }
                }
            } else {
                binding.constraintLayoutEmptyDishes.setVisibility(View.GONE);
                gridAdapter.submitList(dishes);
                isDefaultDishes = false;
            }

        } else {
            final List<DelegateItem> items = new ArrayList<>();
            gridAdapter.submitList(items);
            isDefaultDishes = false;
        }
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    private void updateDish(String chosenId) {
        int index;
        for (int j = 0; j < dishes.size(); j++) {
            if (dishes.get(j) instanceof DishItemDelegateItem) {
                DishItemModel item = ((DishItemDelegateItem) dishes.get(j)).content();
                String currentId = item.getDishId();
                if (currentId.equals(chosenId)) {
                    index = j;
                    ((RestaurantMenuActivity) requireActivity())
                            .openDishDetailsActivity(categoryId, chosenId, updateDishLauncher, index);
                    break;
                }
            }
        }
    }

    private void initCategoriesRecycler(List<CategoryMenuModel> models) {
        if (!models.isEmpty()) {
            for (int i = 0; i < models.size(); i++) {
                CategoryMenuModel current = models.get(i);
                boolean isDefault = false;
                if (i == 0) {
                    isDefault = true;
                    viewModel.getCategoryDishes(restaurantId, current.getCategoryId(), true);
                    categoryId = current.getCategoryId();
                }
                categories.add(new CategoryItemDelegateItem(new CategoryItemModel(
                        i,
                        current.getCategoryId(),
                        current.getName(),
                        current.getTask(),
                        Uri.EMPTY,
                        0,
                        isDefault,
                        isDefault,
                        true,
                        view -> {
                            deleteCategory((View) view, current);
                        },
                        () -> {
                            int position = 0;
                            for (int j = 0; j < categories.size(); j++) {
                                if (categories.get(j) instanceof CategoryItemDelegateItem) {
                                    CategoryItemModel model = (CategoryItemModel) categories.get(j).content();
                                    if (model.isChosen()) {
                                        model.setChosen(false);
                                        horizontalAdapter.notifyItemChanged(j);
                                    }
                                    if (model.getCategoryId().equals(current.getCategoryId())) {
                                        position = j;
                                    }
                                }
                            }

                            CategoryItemModel model = (CategoryItemModel) categories.get(position).content();
                            model.setChosen(true);
                            horizontalAdapter.notifyItemChanged(position);

                            int size = dishes.size();
                            if (size != 0) {
                                dishes.clear();
                            }
                            gridAdapter.notifyItemRangeRemoved(0, size);
                            categoryId = current.getCategoryId();

                            viewModel.getCategoryDishes(restaurantId, categoryId, isDefaultDishes);
                        })));
            }
        } else {
            binding.progressBar.getRoot().setVisibility(View.GONE);
            binding.constraintLayoutEmptyCategories.setVisibility(View.VISIBLE);
            binding.buttonAddDish.setVisibility(View.GONE);
            viewModel.setSuccess();
        }
        categories.add(new AddToppingDelegateItem(new AddToppingModel(
                categories.size() + 1,
                this::launchAddCategory
        )));
        horizontalAdapter.submitList(categories);
    }

    private void deleteCategory(View view, CategoryMenuModel current) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.delete_category_menu, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
            int position = 0;
            for (int j = 0; j < categories.size(); j++) {
                if (categories.get(j) instanceof CategoryItemDelegateItem) {
                    CategoryItemModel currentModel = ((CategoryItemDelegateItem) categories.get(j)).content();
                    if (currentModel.getCategoryId().equals(current.getCategoryId())) {
                        position = j;
                    }
                }
            }
            viewModel.deleteCategory(position, restaurantId, current.getCategoryId());
            return true;
        });
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