package com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_VARIANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRequiredChoiceBinding;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.recycler.RequiredChoiceEditDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.recycler.RequiredChoiceEditDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.recycler.RequiredChoiceEditItemModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegate;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextModel;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonModel;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;

public class  AddRequiredChoiceFragment extends Fragment {

    private AddRequiredChoiceViewModel viewModel;
    private FragmentRequiredChoiceBinding binding;
    private final MainAdapter adapter = new MainAdapter();
    List<DelegateItem> items = new ArrayList<>();
    private String page, restaurantId, categoryId, dishId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddRequiredChoiceViewModel.class);
        restaurantId = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(COMPANY_ID, null);
        try {
            page = AddRequiredChoiceFragmentArgs.fromBundle(getArguments()).getPage();
            categoryId = AddRequiredChoiceFragmentArgs.fromBundle(getArguments()).getCategoryId();
            dishId = AddRequiredChoiceFragmentArgs.fromBundle(getArguments()).getDishId();
        } catch (IllegalArgumentException e) {
            page = requireActivity().getIntent().getStringExtra(PAGE_KEY);
            categoryId = requireActivity().getIntent().getStringExtra(CATEGORY_ID);
            dishId = requireActivity().getIntent().getStringExtra(DISH_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRequiredChoiceBinding.inflate(inflater, container, false);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.setArgumentsNull();
    }

    private void setAdapter() {
        adapter.addDelegate(new TextViewHeaderDelegate());
        adapter.addDelegate(new FloatingActionButtonDelegate());
        adapter.addDelegate(new EditTextDelegate());
        adapter.addDelegate(new RequiredChoiceEditDelegate());

        binding.recyclerView.setAdapter(adapter);
    }

    private void setupObserves() {
        viewModel.isComplete.observe(getViewLifecycleOwner(), choiceId -> {
            if (choiceId != null) {
                Bundle bundle = new Bundle();

                ArrayList<String> arrayList = new ArrayList<>(RequiredChoiceArguments.variants);

                bundle.putString(CHOICE_ID, choiceId);
                bundle.putString(CHOICE_NAME, RequiredChoiceArguments.name);
                bundle.putStringArrayList(CHOICE_VARIANT, arrayList);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_addRequiredChoiceFragment_to_requiredChoiceSuccessfullyAdded, bundle);
            }
        });
    }


    private void onPageInit() {

        switch (page) {
            case PAGE_1:
                items.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_choice_name, 24)));
                items.add(new EditTextDelegateItem(new EditTextModel(3, R.string.choice_name, RequiredChoiceArguments.name, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                    RequiredChoiceArguments.name = stringName;
                })));
                break;
            case PAGE_2:
                items.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.add_at_least_2_variant, 24)));
                items.add(new RequiredChoiceEditDelegateItem(new RequiredChoiceEditItemModel(1, () -> {
                }, name -> {
                    addToVariants(0, name);
                })));
                items.add(new RequiredChoiceEditDelegateItem(new RequiredChoiceEditItemModel(2, () -> {
                }, name -> {
                        addToVariants(1, name);
                })));
                items.add(new FloatingActionButtonDelegateItem(new FloatingActionButtonModel(3, this::addNewRequiredChoice)));
                break;
        }
        adapter.submitList(items);
    }

    private void addNewRequiredChoice() {
        List<DelegateItem> newItems = new ArrayList<>(items);
        newItems.remove(newItems.size() - 1);
        int index = items.size() - 2;
        Log.d("Index", "" + index);
        newItems.add(new RequiredChoiceEditDelegateItem(new RequiredChoiceEditItemModel(index, () -> {
        }, name -> {
            addToVariants(index, name);
        })));
        newItems.add(new FloatingActionButtonDelegateItem(new FloatingActionButtonModel(3, this::addNewRequiredChoice)));
        adapter.submitList(newItems);
        items = newItems;
    }

    private void addToVariants(int index, String name){
        if (RequiredChoiceArguments.variants.size() > index) {
            if (RequiredChoiceArguments.variants.get(index) != null) {
                RequiredChoiceArguments.variants.remove(index);
            }
        }
        RequiredChoiceArguments.variants.add(index, name);
    }

    private void initNextButton() {
        binding.buttonNext.setOnClickListener(v -> {
            navigateNext();
        });
    }

    private void initBackButtonPressed() {
        getActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack();
            }
        });
    }

    private void navigateNext() {
        switch (page) {
            case PAGE_1:
                if (RequiredChoiceArguments.name != null) {
                    NavHostFragment.findNavController(this)
                            .navigate(AddRequiredChoiceFragmentDirections.actionAddRequiredChoiceFragmentSelf(dishId, categoryId, PAGE_2));
                } else {
                    Snackbar.make(requireView(), getString(R.string.name_cannot_be_null), Snackbar.LENGTH_LONG).show();
                }
                break;
            case PAGE_2:
                if (RequiredChoiceArguments.variants.size() >= 2) {
                    viewModel.addChoice(restaurantId, categoryId, dishId);
                } else {
                    Snackbar.make(requireView(), getString(R.string.you_have_to_add_at_least_2_variants), Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void navigateBack() {
        switch (page) {
            case PAGE_1:
                viewModel.setArgumentsNull();
                requireActivity().finish();
                break;
            case PAGE_2:
                NavHostFragment.findNavController(this)
                        .navigate(AddRequiredChoiceFragmentDirections.actionAddRequiredChoiceFragmentSelf(dishId, categoryId, PAGE_2));
                break;
        }
    }
}