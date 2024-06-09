package com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.AddToppingArguments.image;
import static com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.AddToppingArguments.name;
import static com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.AddToppingArguments.price;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_3;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_IMAGE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_PRICE;

import android.content.Context;
import android.content.Intent;
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
import com.example.myapplication.databinding.FragmentAddToppingBinding;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.recyclerView.ToppingDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.recyclerView.ToppingItemDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.recyclerView.ToppingModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextModel;
import myapplication.android.ui.recycler.ui.items.items.priceWithCurrency.PriceWithCurrencyDelegate;
import myapplication.android.ui.recycler.ui.items.items.priceWithCurrency.PriceWithCurrencyDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.priceWithCurrency.PriceWithCurrencyModel;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;

public class AddToppingFragment extends Fragment {

    private AddToppingViewModel viewModel;
    private FragmentAddToppingBinding binding;
    private MainAdapter adapter = new MainAdapter();
    private ActivityResultLauncher<Intent> launcherToppings;
    private final List<DelegateItem> items = new ArrayList<>();
    private String page, restaurantId, categoryId, dishId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddToppingViewModel.class);
        restaurantId = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(COMPANY_ID, null);

        try {
            page = AddToppingFragmentArgs.fromBundle(getArguments()).getPage();
            categoryId = AddToppingFragmentArgs.fromBundle(getArguments()).getCategoryId();
            dishId = AddToppingFragmentArgs.fromBundle(getArguments()).getDishId();
        } catch (IllegalArgumentException e) {
            page = requireActivity().getIntent().getStringExtra(PAGE_KEY);
            categoryId = requireActivity().getIntent().getStringExtra(CATEGORY_ID);
            dishId = requireActivity().getIntent().getStringExtra(DISH_ID);
        }

        if (page.equals(PAGE_3)){
            setLauncher();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddToppingBinding.inflate(inflater, container, false);
        onPageInit();
        initBackButtonPressed();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setupObserves();
        initNextButton();
        initBackButton();
        initCloseButton();
        initBackButtonPressed();
    }

    private void initCloseButton() {
        binding.buttonClose.setOnClickListener(v -> {
            requireActivity().finish();
            viewModel.setArgumentsNull();
        });
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            navigateBack();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        viewModel.setArgumentsNull();
    }

    private void setupObserves() {
        viewModel.isComplete.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                Bundle bundle = new Bundle();
                bundle.putString(TOPPING_NAME, name);
                bundle.putString(TOPPING_PRICE, price);
                bundle.putString(TOPPING_IMAGE, String.valueOf(image));

                viewModel.setArgumentsNull();
                binding.loader.setVisibility(View.GONE);
                binding.buttonNext.setEnabled(true);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_addToppingFragment_to_toppingSuccessfullyAdded, bundle);
            }
        });
    }

    private void setAdapter() {
        adapter.addDelegate(new EditTextDelegate());
        adapter.addDelegate(new TextViewHeaderDelegate());
        adapter.addDelegate(new ToppingItemDelegate());
        adapter.addDelegate(new PriceWithCurrencyDelegate());

        binding.recyclerView.setAdapter(adapter);
    }

    private void initNextButton() {
        binding.buttonNext.setOnClickListener(v -> {
            navigateNext();
        });
    }

    private void onPageInit() {
        switch (page) {
            case PAGE_1:
                binding.progressBar.setProgress(0);
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_topping_name, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.name, name, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                            name = stringName;
                        }))
                });
                break;
            case PAGE_2:
                binding.progressBar.setProgress(40, true);
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_topping_price, 24)),
                        new PriceWithCurrencyDelegateItem(new PriceWithCurrencyModel(3, price, string -> {
                            price = string;
                        }))
                });
                break;
            case PAGE_3:
                binding.progressBar.setProgress(70, true);
                items.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.add_topping_photo, 24)));
                items.add(new ToppingDelegateItem(new ToppingModel(2, name, price, image, this::initImagePicker)));
                adapter.submitList(items);
                break;
        }
    }

    private void initBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack();
            }
        });
    }

    private void navigateBack() {
        switch (page) {
            case PAGE_1:
                requireActivity().finish();
                break;
            case PAGE_2:
                NavHostFragment.findNavController(this)
                        .navigate(AddToppingFragmentDirections.actionAddToppingFragmentSelf(PAGE_1, categoryId, dishId));
                break;
            case PAGE_3:
                NavHostFragment.findNavController(this)
                        .navigate(AddToppingFragmentDirections.actionAddToppingFragmentSelf(PAGE_2, categoryId, dishId));
                break;
        }
    }

    private void navigateNext() {
        switch (page) {
            case PAGE_1:
                if (name != null) {
                    NavHostFragment.findNavController(this)
                            .navigate(AddToppingFragmentDirections.actionAddToppingFragmentSelf(PAGE_2, categoryId, dishId));
                } else {
                    Snackbar.make(requireView(), getString(R.string.name_cannot_be_null), Snackbar.LENGTH_LONG).show();
                }
                break;
            case PAGE_2:
                if (price != null) {
                    NavHostFragment.findNavController(this)
                            .navigate(AddToppingFragmentDirections.actionAddToppingFragmentSelf(PAGE_3, categoryId, dishId));
                } else {
                    Snackbar.make(requireView(), getString(R.string.this_data_is_required), Snackbar.LENGTH_LONG).show();
                }
                break;
            case PAGE_3:
                if (image != null) {
                    binding.loader.setVisibility(View.VISIBLE);
                    binding.buttonNext.setEnabled(false);
                    viewModel.initData(restaurantId, categoryId, dishId);
                } else {
                    Snackbar.make(requireView(), getString(R.string.this_data_is_required), Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void initImagePicker() {
        ImagePicker.with(this)
                .crop()
                .compress(512)
                .maxResultSize(512, 512)
                .createIntent(intent -> {
                    launcherToppings.launch(intent);
                    return null;
                });
    }

    private void setLauncher(){
        launcherToppings = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            image = data.getData();
                            ToppingModel model = (ToppingModel) items.get(items.size() - 1).content();
                            model.setUri(image);
                            adapter.notifyItemChanged(items.size() - 1);
                        }
                    }
                });
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = Arrays.asList(items);
        adapter.submitList(list);
        binding.progressBar.setVisibility(View.GONE);
    }
}