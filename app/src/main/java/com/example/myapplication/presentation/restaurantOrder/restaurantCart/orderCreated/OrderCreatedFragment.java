package com.example.myapplication.presentation.restaurantOrder.restaurantCart.orderCreated;

import static com.example.myapplication.presentation.utils.constants.Restaurant.ORDER_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_PATH;
import static com.example.myapplication.presentation.utils.constants.Utils.STATE;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentOrderCreatedBinding;

public class OrderCreatedFragment extends Fragment {

    private FragmentOrderCreatedBinding binding;
    private String tablePath, orderId, restaurantId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        tablePath = requireActivity().getIntent().getStringExtra(TABLE_PATH);
        orderId = requireActivity().getIntent().getStringExtra(ORDER_ID);
        restaurantId = requireActivity().getIntent().getStringExtra(RESTAURANT);
        binding = FragmentOrderCreatedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((OrderCreatedActivity)requireActivity()).startService(tablePath, orderId, restaurantId);
        initButtonOk();
        initBackButton();
        handleBackButtonPressed();
        initInfoBox();
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack();
            }
        });
    }

    private void initInfoBox() {
        binding.infoBox.body.setText(getString(R.string.your_order_is_being_prepared_we_wish_you_a_nice_day));
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            navigateBack();
        });
    }

    private void initButtonOk() {
        binding.buttonOk.setOnClickListener(v -> {
            navigateBack();
        });
    }

    private void navigateBack() {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        requireActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }
}