package com.example.myapplication.presentation.restaurantMenu.addDish;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.imageUri;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.ingredients;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.name;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.price;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.timeCooking;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.weightCount;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_3;
import static com.example.myapplication.presentation.utils.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.Utils.PAGE_5;
import static com.example.myapplication.presentation.utils.Utils.PAGE_6;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
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
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAddDishBinding;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextModel;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;

public class AddDishFragment extends Fragment {

    private AddDishViewModel viewModel;
    private FragmentAddDishBinding binding;
    private MainAdapter mainAdapter = new MainAdapter();
    private List<DelegateItem> items = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;
    private String page, restaurantId, categoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddDishViewModel.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        try {
            page = AddDishFragmentArgs.fromBundle(getArguments()).getPage();
            categoryId = AddDishFragmentArgs.fromBundle(getArguments()).getId();
        } catch (IllegalArgumentException e) {
            page = requireActivity().getIntent().getStringExtra(PAGE_KEY);
            categoryId = requireActivity().getIntent().getStringExtra(CATEGORY_ID);
        }
        setLauncher();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddDishBinding.inflate(inflater, container, false);
        onPageInit(page);
        initBackButtonPressed();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (page.equals(PAGE_6)) {
            binding.buttonNext.setText(R.string.add);
        }

        setMainAdapter();
        initProgress();
        initNextButton();
        initCloseButton();
        initBackButton();
        setupObserves();
    }

    private void initProgress() {
        switch (page) {
            case PAGE_2:
                binding.companyProgressBar.setProgress(17);
                break;
            case PAGE_3:
                binding.companyProgressBar.setProgress(34);
                break;
            case PAGE_4:
                binding.companyProgressBar.setProgress(51);
                break;
            case PAGE_5:
                binding.companyProgressBar.setProgress(68);
                break;
            case PAGE_6:
                binding.companyProgressBar.setProgress(85);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainAdapter = null;
        viewModel.setArgumentsNull();
    }

    private void initCloseButton() {
        binding.buttonClose.setOnClickListener(v -> {
            requireActivity().finish();
            viewModel.setArgumentsNull();
        });
    }

    private void setMainAdapter() {
        mainAdapter.addDelegate(new EditTextDelegate());
        mainAdapter.addDelegate(new TextViewHeaderDelegate());
        mainAdapter.addDelegate(new DishItemDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.onComplete.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                viewModel.setArgumentsNull();
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_addDishFragment_to_dishAddedSuccessfullyFragment);
            }
        });
    }

    private void initBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack(page);
            }
        });
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = new ArrayList<>(Arrays.asList(items));
        mainAdapter.submitList(list);
    }

    private void initNextButton() {
        binding.buttonNext.setOnClickListener(v -> {
            navigateNext(page);
        });
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            navigateBack(page);
        });
    }

    public void navigateBack(String page) {
        switch (page) {
            case PAGE_1:
                requireActivity().finish();
                break;
            case PAGE_2:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_1, categoryId));
                break;
            case PAGE_3:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_2, categoryId));
                break;
            case PAGE_4:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_3, categoryId));
                break;
            case PAGE_5:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_4, categoryId));
                break;
            case PAGE_6:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_5, categoryId));
                break;
        }
    }

    private void navigateNext(String page) {
        switch (page) {
            case PAGE_1:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_2, categoryId));
                break;
            case PAGE_2:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_3, categoryId));
                break;
            case PAGE_3:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_4, categoryId));
                break;
            case PAGE_4:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_5, categoryId));
                break;
            case PAGE_5:
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_6, categoryId));
                break;
            case PAGE_6:
                if (imageUri != null){
                    viewModel.initDishData(restaurantId, categoryId);
                }
                break;
        }

    }

    private void onPageInit(String page) {
        switch (page) {
            case PAGE_1:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_dish_name, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.name, name, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                            name  = stringName;
                        }))
                });
                break;
            case PAGE_2:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_ingredients, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.ingredients_potato_tomato_and_etc, ingredients, InputType.TYPE_CLASS_TEXT, true, string -> {
                            ingredients = string;
                        }))
                });
                break;
            case PAGE_3:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_weight_or_amount_of_dish, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.weight_amount, weightCount, InputType.TYPE_CLASS_TEXT, true, string -> {
                            weightCount = string;
                        }))
                });
                break;
            case PAGE_4:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_price, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.price, price, InputType.TYPE_CLASS_TEXT, true, string -> {
                            price = string;
                        }))
                });
                break;
            case PAGE_5:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.estimated_time_cooking, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.time, timeCooking, InputType.TYPE_CLASS_TEXT, true, string -> {
                            timeCooking  = string;
                        }))
                });
                break;
            case PAGE_6:
                items.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.add_dish_photo, 24)));
                items.add(new DishItemDelegateItem(new DishItemModel(2, name, weightCount, price, imageUri, null, this::initImagePicker)));
                mainAdapter.submitList(items);
                break;
        }
    }

    private void setLauncher() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri = data.getData();
                            List<DelegateItem> newItems = new ArrayList<>(items);
                            newItems.remove(items.size() - 1);
                            newItems.add(new DishItemDelegateItem(new DishItemModel(2, name, weightCount, price, imageUri, null, this::initImagePicker)));
                            mainAdapter.submitList(newItems);
                            items = newItems;
                        }
                    }
                });
    }

    private void initImagePicker() {
        ImagePicker.with(this)
                .crop()
                .compress(512)
                .maxResultSize(512, 512)
                .createIntent(intent -> {
                    launcher.launch(intent);
                    return null;
                });
    }
}