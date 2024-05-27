package com.example.myapplication.presentation.restaurantMenu.AddCategory.main;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.URI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.example.myapplication.databinding.FragmentAddCategoryBinding;
import com.example.myapplication.presentation.basicQueue.createQueue.mainFragment.CreateQueueFragmentArgs;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageAddOwnPhoto.CategoryOwnImageDelegate;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.chooseCategoryImage.ChooseCategoryImageDelegate;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.chooseCategoryImage.ChooseCategoryImageDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.chooseCategoryImage.ChooseCategoryImageModel;
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
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegate;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;

public class AddCategoryFragment extends Fragment {

    private AddCategoryViewModel viewModel;
    private FragmentAddCategoryBinding binding;
    private MainAdapter mainAdapter = new MainAdapter();
    private final List<DelegateItem> items = new ArrayList<>();
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri imageUri = Uri.EMPTY;
    private String page, restaurantId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddCategoryViewModel.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        try {
            page = CreateQueueFragmentArgs.fromBundle(getArguments()).getPage();
        } catch (IllegalArgumentException e) {
            page = requireActivity().getIntent().getStringExtra(PAGE_KEY);
        }
        setLauncher();
    }

    private void setLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            ArgumentsCategory.chosenImage = data.getData();
                            imageUri = data.getData();
                            ChooseCategoryImageModel model = (ChooseCategoryImageModel) items.get(items.size() - 1).content();
                            model.setUri(imageUri);
                            mainAdapter.notifyItemChanged(items.size() - 1);
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false);
        onPageInit(page);
        initBackButtonPressed();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (page.equals(PAGE_2)) {
            binding.buttonNext.setText(R.string.add);
            binding.companyProgressBar.setProgress(50, true);
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

    private void onPageInit(String page) {
        switch (page) {
            case PAGE_1:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_category_name, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.name, ArgumentsCategory.name, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                            ArgumentsCategory.name = stringName;
                        }))
                });
                break;

            case PAGE_2:
                items.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.enter_category_name, 24)));
                items.add(new ChooseCategoryImageDelegateItem(new ChooseCategoryImageModel(2, getDrawables(), ArgumentsCategory.name, imageUri, this::initImagePicker)));
                mainAdapter.submitList(items);
                break;
        }
    }

    private List<Integer> getDrawables() {
        return Arrays.asList(
                R.drawable.beer_image,
                R.drawable.desserts_image,
                R.drawable.garnish_image,
                R.drawable.juice_image,
                R.drawable.pasta_image,
                R.drawable.pizza_image,
                R.drawable.salad_image,
                R.drawable.sauce_image,
                R.drawable.soup_image,
                R.drawable.sushi_image,
                R.drawable.wok_noodles_image,
                R.drawable.cold_dishes_image,
                R.drawable.flour_products_image,
                R.drawable.fast_food_image
        );
    }

    private void navigateNext(String page) {
        switch (page) {
            case PAGE_1:
                if (ArgumentsCategory.name == null) {
                    Snackbar.make(requireView(), getString(R.string.name_cannot_be_null), Snackbar.LENGTH_LONG).show();
                } else {
                    NavHostFragment.findNavController(this)
                            .navigate(AddCategoryFragmentDirections.actionAddCategoryFragmentSelf(PAGE_2));
                }
                break;
            case PAGE_2:
                if (ArgumentsCategory.chosenImage != Uri.EMPTY) {
                    Log.d("chosen image", String.valueOf(ArgumentsCategory.chosenImage));
                    binding.loader.setVisibility(View.VISIBLE);
                    binding.buttonNext.setEnabled(false);
                    viewModel.initCategoryData(restaurantId);
                } else {
                    Snackbar.make(requireView(), getString(R.string.this_data_is_required), Snackbar.LENGTH_LONG).show();
                }
                break;
        }

    }

    private void initImagePicker() {
        ImagePicker.with(this)
                .cropSquare()
                .compress(512)
                .maxResultSize(512, 512)
                .createIntent(intent -> {
                    activityResultLauncher.launch(intent);
                    return null;
                });
    }

    public void navigateBack(String page) {
        switch (page) {
            case PAGE_1:
                requireActivity().finish();
                break;
            case PAGE_2:
                NavHostFragment.findNavController(this)
                        .navigate(AddCategoryFragmentDirections.actionAddCategoryFragmentSelf(PAGE_1));
                break;

        }
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            navigateBack(page);
        });
    }

    private void initNextButton() {
        binding.buttonNext.setOnClickListener(v -> {
            navigateNext(page);
        });
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
        mainAdapter.addDelegate(new ChooseCategoryImageDelegate());
        mainAdapter.addDelegate(new CategoryOwnImageDelegate());

        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves() {

        viewModel.onComplete.observe(getViewLifecycleOwner(), category -> {
            if (category != null) {
                binding.loader.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.putExtra(COMPANY_ID, category.getId());
                intent.putExtra(URI, String.valueOf(category.getUri()));
                intent.putExtra(COMPANY_NAME, category.getName());
                requireActivity().setResult(RESULT_OK, intent);
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
        List<DelegateItem> list = new ArrayList<>(Arrays.asList(items));
        mainAdapter.submitList(list);
    }
}