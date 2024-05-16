package com.example.myapplication.presentation.restaurantMenu.dishDetails;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_VARIANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_IMAGE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_PRICE;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDishDetailsBinding;
import com.example.myapplication.presentation.dialogFragments.deleteToppingDialog.DeleteToppingDialogFragment;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.IngredientsToRemoveDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.EditProfileFragment;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.AddRequiredChoiceActivity;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.AddToppingsActivity;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.model.DishDetailsStateModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.model.RequiredChoiceDishDetailsModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping.AddToppingDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping.AddToppingDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.addTopping.AddToppingModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.choiceHeader.RequiredChoiceHeaderDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.choiceHeader.RequiredChoiceHeaderDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.choiceHeader.RequiredChoiceHeaderModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.requiredChoice.RequiredChoiceAdapter;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.requiredChoice.RequiredChoiceItemModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.topping.ToppingDelegate;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.topping.ToppingDishDetailsDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.topping.ToppingDishDetailsModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.state.DishDetailsState;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonModel;
import myapplication.android.ui.recycler.ui.items.items.horizontalRecycler.HorizontalRecyclerDelegate;
import myapplication.android.ui.recycler.ui.items.items.horizontalRecycler.HorizontalRecyclerDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.horizontalRecycler.HorizontalRecyclerModel;

public class DishDetailsFragment extends Fragment {

    private DishDetailsViewModel viewModel;
    private FragmentDishDetailsBinding binding;
    private String dishId, categoryId, restaurantId;
    private Uri imageUri = Uri.EMPTY;
    private ActivityResultLauncher<Intent> addToppingLauncher, launcherRequiredChoices, imageLauncher;
    private List<DelegateItem> requiredChoiceItems = new ArrayList<>();
    private List<DelegateItem> toppingItems = new ArrayList<>();
    private final MainAdapter requiredChoiceAdapter = new MainAdapter();
    private final MainAdapter toppingsAdapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DishDetailsViewModel.class);
        dishId = getActivity().getIntent().getStringExtra(DISH_ID);
        categoryId = getActivity().getIntent().getStringExtra(CATEGORY_ID);
        restaurantId = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(COMPANY_ID, null);
        setToppingLauncher();
        setRequiredChoiceLauncher();
        setImageLauncher();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDishDetailsBinding.inflate(inflater, container, false);
        viewModel.getDishData(restaurantId, categoryId, dishId);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRequiredChoiceAdapter();
        setToppingAdapter();
        setupObserves();
        initSaveButton();
        initRemoveIngredients();
        initChangePhoto();
    }

    private void initChangePhoto() {
        binding.foodImage.setOnClickListener(v -> {
            initImagePicker();
        });
    }

    private void initImagePicker() {
        ImagePicker.with(this)
                .cropSquare()
                .compress(512)
                .maxResultSize(512, 512)
                .createIntent(intent -> {
                    imageLauncher.launch(intent);
                    return null;
                });
    }

    private void setImageLauncher() {
        imageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri = data.getData();
                            Glide.with(requireContext())
                                    .load(imageUri)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(binding.foodImage);
                        }
                    }
                });
    }

    private void initSaveButton() {
        binding.buttonSave.setOnClickListener(v -> {
            String name = binding.nameEditText.getText().toString();
            String price = binding.priceEditText.getText().toString();
            String ingredients = binding.ingredientsEditText.getText().toString();
            String weightOrCount = binding.weightEditText.getText().toString();
            viewModel.saveData(restaurantId, categoryId, dishId, name, ingredients, price, weightOrCount, imageUri);
        });
    }

    private void initRemoveIngredients() {
        binding.toRemoveHeader.setOnClickListener(v -> {
            IngredientsToRemoveDialogFragment dialogFragment = new IngredientsToRemoveDialogFragment(restaurantId, categoryId, dishId);
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "REMOVE_INGREDIENTS_DIALOG");
        });
    }

    private void setToppingAdapter() {
        toppingsAdapter.addDelegate(new ToppingDelegate());
        toppingsAdapter.addDelegate(new AddToppingDelegate());
        binding.toppingsRecycler.setAdapter(toppingsAdapter);
    }

    private void setRequiredChoiceAdapter() {
        requiredChoiceAdapter.addDelegate(new RequiredChoiceHeaderDelegate());
        requiredChoiceAdapter.addDelegate(new HorizontalRecyclerDelegate());
        requiredChoiceAdapter.addDelegate(new FloatingActionButtonDelegate());

        binding.requiredChoiceRecycler.setAdapter(requiredChoiceAdapter);
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof DishDetailsState.Success) {
                DishDetailsStateModel model = ((DishDetailsState.Success) state).data;
                initUi(model);
                binding.errorLayout.getRoot().setVisibility(View.GONE);
            } else if (state instanceof DishDetailsState.Loading) {

            } else if (state instanceof DishDetailsState.Error) {
                setErrorLayout();
            }
        });
        viewModel.isUpdated.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                requireActivity().finish();
            }
        });
    }

    private void setErrorLayout() {
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getDishData(restaurantId, categoryId, dishId);
        });
    }

    private void setToppingLauncher() {
        addToppingLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name = data.getStringExtra(TOPPING_NAME);
                            String price = data.getStringExtra(TOPPING_PRICE);
                            Uri image = Uri.parse(data.getStringExtra(TOPPING_IMAGE));
                            addNewTopping(name, price, image);
                        }
                    }
                });
    }

    private void setRequiredChoiceLauncher() {
        launcherRequiredChoices = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name = data.getStringExtra(CHOICE_NAME);
                            String choiceId = data.getStringExtra(CHOICE_ID);
                            ArrayList<String> variant = data.getStringArrayListExtra(CHOICE_VARIANT);
                            addNewRequireChoice(name, choiceId, variant);
                        }
                    }
                });
    }

    private void addNewRequireChoice(String name, String choiceId, ArrayList<String> variants) {
        List<DelegateItem> newItems = new ArrayList<>(requiredChoiceItems);
        newItems.remove(requiredChoiceItems.size() - 1);
        RequiredChoiceAdapter adapter = new RequiredChoiceAdapter();
        newItems.add(new RequiredChoiceHeaderDelegateItem(new RequiredChoiceHeaderModel(requiredChoiceItems.size() - 1, name, () -> {

            Bundle bundle = new Bundle();

            bundle.putString(CHOICE_ID, choiceId);
            bundle.putString(CATEGORY_ID, categoryId);
            bundle.putString(DISH_ID, dishId);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_dishDetailsFragment_to_editRequiredChoiceFragment, bundle);
        })));

        List<RequiredChoiceItemModel> choiceItemModels = new ArrayList<>();

        for (int i = 0; i < variants.size(); i++) {
            String choice = variants.get(i);
            choiceItemModels.add(new RequiredChoiceItemModel(i, choice));
        }

        newItems.add(new HorizontalRecyclerDelegateItem(new HorizontalRecyclerModel(requiredChoiceItems.size(), choiceItemModels, adapter)));
        newItems.add(new FloatingActionButtonDelegateItem(new FloatingActionButtonModel(requiredChoiceItems.size(), () -> {
           openAddRequiredChoiceActivity(categoryId, dishId);
        })));

        requiredChoiceAdapter.submitList(newItems);
        requiredChoiceItems = newItems;
    }

    private void addNewTopping(String name, String price, Uri image) {
        List<DelegateItem> newItems = new ArrayList<>(toppingItems);
        newItems.remove(toppingItems.size() - 1);
        newItems.add(new ToppingDishDetailsDelegateItem(new ToppingDishDetailsModel(toppingItems.size(), name, price, null, image,
                () -> {
                    showDeleteToppingDialog(restaurantId, categoryId, dishId, name, toppingItems.size());
                })));
        newItems.add(new AddToppingDelegateItem(new AddToppingModel(toppingItems.size(), () -> {
            openAddToppingsActivity(categoryId, dishId);
        })));
        toppingsAdapter.submitList(newItems);
        toppingItems = newItems;
    }

    private void initUi(DishDetailsStateModel model) {
        Glide.with(requireContext())
                .load(model.getUri())
                .into(binding.foodImage);

        binding.nameEditText.setText(model.getName());
        binding.weightEditText.setText(model.getWeightOrCount());
        binding.ingredientsEditText.setText(model.getIngredients());
        binding.priceEditText.setText(model.getPrice().concat("₽"));

        binding.buttonInfo.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.dish_details_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.getMenu().getItem(0).setTitle(model.getEstimatedTime());
        });

        binding.buttonEditIngredients.setOnClickListener(v -> {

        });
        initRequiredChoiceRecycler(model.getModels());
        initToppingsRecycler(model.getToppings());
    }

    private void initToppingsRecycler(List<VariantsModel> variants) {
        if (variants != null && variants.size() > 0) {
            for (int i = 0; i < variants.size(); i++) {
                VariantsModel current = variants.get(i);
                int index = i;
                toppingItems.add(new ToppingDishDetailsDelegateItem(new ToppingDishDetailsModel(i, current.getName(), current.getPrice(), current.getUri(), null,
                        () -> {
                            showDeleteToppingDialog(restaurantId, categoryId, dishId, current.getName(), index);
                        })));
            }
        }
        toppingItems.add(new AddToppingDelegateItem(new AddToppingModel(toppingItems.size(), () -> {
            openAddToppingsActivity(categoryId, dishId);
        })));
        toppingsAdapter.submitList(toppingItems);
    }

    private void openAddToppingsActivity(String categoryId, String dishId) {
        Intent intent = new Intent(requireActivity(), AddToppingsActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        addToppingLauncher.launch(intent);
    }

    private void showDeleteToppingDialog(String restaurantId, String categoryId, String dishId, String name, int index) {
        DeleteToppingDialogFragment dialogFragment = new DeleteToppingDialogFragment(restaurantId, categoryId, dishId, name);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "DELETE_TOPPING_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            removeToppingFromRecycler(index);
        });
    }

    private void removeToppingFromRecycler(int index) {
        List<DelegateItem> newItems = new ArrayList<>(toppingItems);
        newItems.remove(index);
        toppingsAdapter.submitList(newItems);
        toppingItems = newItems;
    }

    private void initRequiredChoiceRecycler(List<RequiredChoiceDishDetailsModel> models) {

        if (models != null && models.size() > 0) {
            for (int i = 0; i < models.size(); i++) {
                RequiredChoiceDishDetailsModel current = models.get(i);
                RequiredChoiceAdapter adapter = new RequiredChoiceAdapter();
                List<RequiredChoiceItemModel> choiceItemModels = new ArrayList<>();
                for (String choice : current.getVariantsName()) {
                    choiceItemModels.add(new RequiredChoiceItemModel(i, choice));
                }
                requiredChoiceItems.add(new RequiredChoiceHeaderDelegateItem(new RequiredChoiceHeaderModel(i, current.getName(), () -> {

                    Bundle bundle = new Bundle();

                    bundle.putString(CHOICE_ID, current.getId());
                    bundle.putString(CATEGORY_ID, categoryId);
                    bundle.putString(DISH_ID, dishId);

                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_dishDetailsFragment_to_editRequiredChoiceFragment, bundle);
                })));
                requiredChoiceItems.add(new HorizontalRecyclerDelegateItem(new HorizontalRecyclerModel(i + 1, choiceItemModels, adapter)));
            }
        }
        requiredChoiceItems.add(new FloatingActionButtonDelegateItem(new FloatingActionButtonModel(requiredChoiceItems.size(), () -> {
            openAddRequiredChoiceActivity(categoryId, dishId);
        })));
        requiredChoiceAdapter.submitList(requiredChoiceItems);
    }

    public void openAddRequiredChoiceActivity(String categoryId, String dishId){
        Intent intent = new Intent(requireActivity(), AddRequiredChoiceActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(DISH_ID, dishId);
        launcherRequiredChoices.launch(intent);
    }

}