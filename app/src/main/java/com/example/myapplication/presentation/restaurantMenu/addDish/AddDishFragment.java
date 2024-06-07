package com.example.myapplication.presentation.restaurantMenu.addDish;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.imageUri;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.ingredients;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.name;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.price;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.timeCooking;
import static com.example.myapplication.presentation.restaurantMenu.addDish.DishArguments.weightCount;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.HOURS;
import static com.example.myapplication.presentation.utils.constants.Utils.MINUTES;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_3;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_5;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_6;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.SECONDS;
import static com.example.myapplication.presentation.utils.constants.Utils.URI;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_WEIGHT_OR_COUNT;

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
import com.example.myapplication.presentation.restaurantMenu.addDish.recycler.NumberPickerDelegate;
import com.example.myapplication.presentation.restaurantMenu.addDish.recycler.NumberPickerDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.addDish.recycler.NumberPickerModel;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishItem.DishItemModel;
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

public class AddDishFragment extends Fragment {

    private AddDishViewModel viewModel;
    private FragmentAddDishBinding binding;
    private MainAdapter mainAdapter = new MainAdapter();
    private List<DelegateItem> items = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;
    List<DelegateItem> list = new ArrayList<>();
    private String page, restaurantId, categoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddDishViewModel.class);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
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

        initNextButton();
        initCloseButton();
        initBackButton();
        setupObserves();
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
        mainAdapter.addDelegate(new PriceWithCurrencyDelegate());
        mainAdapter.addDelegate(new NumberPickerDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {
        viewModel.onComplete.observe(getViewLifecycleOwner(), model -> {
            if (model != null) {
                viewModel.setArgumentsNull();
                Intent intent = new Intent();
                intent.putExtra(DISH_ID, model.getDishId());
                intent.putExtra(DISH_NAME, model.getName());
                intent.putExtra(DISH_PRICE, model.getPrice());
                intent.putExtra(DISH_WEIGHT_OR_COUNT, model.getWeightCount());
                intent.putExtra(URI, model.getUri().toString());
                requireActivity().setResult(RESULT_OK, intent);
                binding.loader.setVisibility(View.GONE);
                requireActivity().finish();
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
       list.addAll(Arrays.asList(items));
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
                if (name != null) {
                    NavHostFragment.findNavController(this)
                            .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_2, categoryId));
                } else {
                    Snackbar.make(requireView(), getString(R.string.name_cannot_be_null), Snackbar.LENGTH_LONG).show();
                }
                break;
            case PAGE_2:
                if (ingredients != null) {
                    NavHostFragment.findNavController(this)
                            .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_3, categoryId));
                } else {
                    Snackbar.make(requireView(), getString(R.string.this_field_is_required), Snackbar.LENGTH_LONG).show();
                }
                break;
            case PAGE_3:
                if (weightCount != null) {
                    NavHostFragment.findNavController(this)
                            .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_4, categoryId));
                } else {
                    Snackbar.make(requireView(), getString(R.string.this_field_is_required), Snackbar.LENGTH_LONG).show();
                }
                break;
            case PAGE_4:
                if (price != null) {
                    if (Integer.parseInt(price) <= 1000000) {
                        NavHostFragment.findNavController(this)
                                .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_5, categoryId));
                    } else {
                        Snackbar.make(requireView(), getString(R.string.this_price_should_be_less), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(requireView(), getString(R.string.this_field_is_required), Snackbar.LENGTH_LONG).show();
                }
                break;
            case PAGE_5:
                if (timeCooking == null) {
                    timeCooking = "???";
                }
                NavHostFragment.findNavController(this)
                        .navigate(AddDishFragmentDirections.actionAddDishFragmentSelf(PAGE_6, categoryId));
                break;
            case PAGE_6:
                if (imageUri != null) {
                    binding.loader.setVisibility(View.VISIBLE);
                    binding.buttonNext.setEnabled(false);
                    viewModel.initDishData(restaurantId, categoryId);
                } else {
                    Snackbar.make(requireView(), getString(R.string.this_field_is_required), Snackbar.LENGTH_LONG).show();
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
                            name = stringName;
                        }))
                });
                break;
            case PAGE_2:
                binding.companyProgressBar.setProgress(15, true);
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_ingredients, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.ingredients_potato_tomato_and_etc, ingredients, InputType.TYPE_CLASS_TEXT, true, string -> {
                            ingredients = string;
                        }))
                });
                break;
            case PAGE_3:
                binding.companyProgressBar.setProgress(35, true);
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_weight_or_amount_of_dish, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.weight_amount, weightCount, InputType.TYPE_CLASS_TEXT, true, string -> {
                            weightCount = string;
                        }))
                });
                break;
            case PAGE_4:
                binding.companyProgressBar.setProgress(50, true);
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_price, 24)),
                        new PriceWithCurrencyDelegateItem(new PriceWithCurrencyModel(3, price, string -> {
                            price = string;
                        }))
                });
                break;
            case PAGE_5:
                binding.companyProgressBar.setProgress(70, true);
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.estimated_time_cooking, 24)),
                        new NumberPickerDelegateItem(new NumberPickerModel(2, DishArguments.bundle,
                                bundle -> {
                                    String text = "";
                                    String hours = ((Bundle) bundle).getString(HOURS);
                                    String minutes = ((Bundle) bundle).getString(MINUTES);
                                    String seconds = ((Bundle) bundle).getString(SECONDS);

                                    DishArguments.bundle.putString(HOURS, hours);
                                    DishArguments.bundle.putString(MINUTES, minutes);
                                    DishArguments.bundle.putString(SECONDS, seconds);

                                    if (!hours.equals("0")) {
                                        text = text.concat(hours.concat(getString(R.string.hours_description)).concat(" "));
                                    }
                                    if (!minutes.equals("0")){
                                        text = text.concat(minutes.concat(getString(R.string.minutes_description)).concat(" "));
                                    }
                                    if (!seconds.equals("0")){
                                        text = text.concat(seconds.concat(getString(R.string.minutes_description)));
                                    }
                                    timeCooking = text;
                                }))

                });
                break;
            case PAGE_6:
                binding.companyProgressBar.setProgress(85, true);
                items.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.add_dish_photo, 24)));
                items.add(new DishItemDelegateItem(new DishItemModel(2, null, name, weightCount, price, imageUri, null, true, this::initImagePicker)));
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
                            DishItemModel model = (DishItemModel) items.get(items.size() - 1).content();
                            model.setUri(imageUri);
                            mainAdapter.notifyItemChanged(items.size() - 1);
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