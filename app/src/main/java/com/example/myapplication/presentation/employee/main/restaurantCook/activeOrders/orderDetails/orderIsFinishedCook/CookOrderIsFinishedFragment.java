package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.orderIsFinishedCook;

import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCookOrderIsFinishedBinding;

public class CookOrderIsFinishedFragment extends Fragment {

    private FragmentCookOrderIsFinishedBinding binding;
    private String tableNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tableNumber = requireActivity().getIntent().getStringExtra(TABLE_NUMBER);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCookOrderIsFinishedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOkayButton();
        initInfoBox();
        initButtonBack();
        handleBackButtonPressed();
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void finish() {
        Intent intent = new Intent();
        intent.putExtra(TABLE_NUMBER, tableNumber);
        requireActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void initInfoBox() {
        binding.infoBoxLayout.body.setText(getString(R.string.you_can_now_continue_doing_other_order_and_this_one_will_be_served_soon));
    }

    private void initOkayButton() {
        binding.buttonOk.setOnClickListener(v -> {
            finish();
        });
    }
}