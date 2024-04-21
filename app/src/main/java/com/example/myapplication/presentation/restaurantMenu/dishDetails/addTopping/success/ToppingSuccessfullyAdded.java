package com.example.myapplication.presentation.restaurantMenu.dishDetails.addTopping.success;

import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_IMAGE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_PRICE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentToppingSuccessfullyAddedBinding;

public class ToppingSuccessfullyAdded extends Fragment {

    private FragmentToppingSuccessfullyAddedBinding binding;
    private String price, name;
    private Uri image;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        price = getArguments().getString(TOPPING_PRICE);
        name = getArguments().getString(TOPPING_NAME);
        image = Uri.parse(getArguments().getString(TOPPING_IMAGE));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentToppingSuccessfullyAddedBinding.inflate(inflater, container, false);
        binding.infoBoxLayout.body.setText(getString(R.string.now_your_visitors_can_add_this_to_topping_to_this_dish));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initButtonOk();
        handleBackButtonPressed();
    }

    private void handleBackButtonPressed() {
        getActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack();
            }
        });
    }

    private void navigateBack() {
        Intent intent = new Intent();
        intent.putExtra(TOPPING_PRICE, price);
        intent.putExtra(TOPPING_NAME, name);
        intent.putExtra(TOPPING_IMAGE, String.valueOf(image));

        getActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }

    private void initButtonOk() {
        binding.buttonOk.setOnClickListener(v -> {
            navigateBack();
        });
    }


}
