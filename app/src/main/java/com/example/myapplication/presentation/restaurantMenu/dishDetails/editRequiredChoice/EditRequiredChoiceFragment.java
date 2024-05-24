package com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEditRequiredChoiceBinding;
import com.example.myapplication.presentation.dialogFragments.addNewVariant.AddNewVariantDialogFragment;
import com.example.myapplication.presentation.dialogFragments.deleteRequiredChoice.DeleteRequiredChoiceDialogFragment;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.model.EditRequireChoiceModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.recyclerView.EditRequiredChoiceItemAdapter;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.recyclerView.EditRequiredChoiceItemModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.state.EditRequiredChoiceState;

import java.util.ArrayList;
import java.util.List;

public class EditRequiredChoiceFragment extends Fragment {

    private EditRequiredChoiceViewModel viewModel;
    private FragmentEditRequiredChoiceBinding binding;
    private String restaurantId, categoryId, dishId, choiceId;
    private EditRequiredChoiceItemAdapter adapter = new EditRequiredChoiceItemAdapter();
    private List<EditRequiredChoiceItemModel> items = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EditRequiredChoiceViewModel.class);

        restaurantId = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(COMPANY_ID, null);
        categoryId = getArguments().getString(CATEGORY_ID);
        dishId = getArguments().getString(DISH_ID);
        choiceId = getArguments().getString(CHOICE_ID);
        viewModel.getData(restaurantId, categoryId, dishId, choiceId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditRequiredChoiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initMenu();
        initButtonSave();
        initButtonBack();
        initAddButton();
    }

    private void initButtonSave() {
        binding.buttonSave.setOnClickListener(v -> {
            String name = binding.choiceTitleEditText.getText().toString();
            viewModel.updateRequiredChoiceUseCase(restaurantId, categoryId, dishId, choiceId, name);
        });
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            showAddDialog();
        });
    }

    private void showAddDialog() {
        AddNewVariantDialogFragment dialogFragment = new AddNewVariantDialogFragment(restaurantId, categoryId, dishId, choiceId);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "ADD_NEW_VARIANT_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            String name = bundle.getString(CHOICE_NAME);
            List<EditRequiredChoiceItemModel> newItems = new ArrayList<>(items);
            addVariant(newItems, items.size(), name);
            adapter.submitList(newItems);
            items = newItems;
        });
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            navigateBack();
        });
    }

    private void initMenu() {
        binding.buttonChoiceMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.required_choice_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
                showDeleteChoiceDialog();
                return false;
            });
        });
    }

    private void showDeleteChoiceDialog() {
        DeleteRequiredChoiceDialogFragment dialogFragment = new DeleteRequiredChoiceDialogFragment(restaurantId, categoryId, dishId, choiceId);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "DELETE_REQUIRED_CHOICE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            navigateBack();
        });
    }

    private void navigateBack() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_editRequiredChoiceFragment_to_dishDetailsFragment);
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof EditRequiredChoiceState.Success) {
                EditRequireChoiceModel model = ((EditRequiredChoiceState.Success) state).data;
                binding.choiceTitleEditText.setText(model.getName());
                initRecycler(model.getVariants());
                binding.errorLayout.getRoot().setVisibility(View.GONE);
            } else if (state instanceof EditRequiredChoiceState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof EditRequiredChoiceState.Error) {
                setError();
            }
        });

        viewModel.isChoiceVariantDelete.observe(getViewLifecycleOwner(), index -> {
            if (index != null) {
                int itemIndex = index;
                List<EditRequiredChoiceItemModel> newItems = new ArrayList<>(items);
                newItems.remove(itemIndex);
                adapter.submitList(newItems);
                items = newItems;
            }
        });

        viewModel.isUpdated.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                navigateBack();
            }
        });
    }

    private void setError() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getData(restaurantId, categoryId, dishId, choiceId);
        });
    }

    private void initRecycler(List<String> variants) {
        for (int i = 0; i < variants.size(); i++) {
            String current = variants.get(i);
            addVariant(items, i, current);
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(items);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    private void addVariant(List<EditRequiredChoiceItemModel> items, int index, String name) {
        items.add(new EditRequiredChoiceItemModel(index, name,
                () -> {
                    viewModel.deleteChoiceVariant(restaurantId, categoryId, dishId, choiceId, name, index);
                }, newVariant -> {
                    viewModel.updateVariant(restaurantId, categoryId, dishId, choiceId, name, newVariant);
        }));
    }
}
