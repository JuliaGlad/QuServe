package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable.successfullyAdded;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_ID;

import androidx.activity.OnBackPressedCallback;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentTableSuccessfullyAddedBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable.AddTableActivity;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable.successfullyAdded.state.TableAddedState;

public class TableSuccessfullyAddedFragment extends Fragment {

    private TableSuccessfullyAddedViewModel viewModel;
    private FragmentTableSuccessfullyAddedBinding binding;
    private String tableId, locationId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TableSuccessfullyAddedViewModel.class);
        tableId = getArguments().getString(TABLE_ID);
        locationId = getArguments().getString(LOCATION_ID);
        viewModel.getTableQrCode(tableId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTableSuccessfullyAddedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        handleBackButtonPressed();
        initBackButton();
        initSeeDetailsButton();
        initInfoBox();
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof TableAddedState.Success) {
                Uri uri = ((TableAddedState.Success) state).data;
                Glide.with(requireView())
                        .load(uri)
                        .into(binding.qrCodeImage);
            } else if (state instanceof TableAddedState.Loading) {

            } else if (state instanceof TableAddedState.Error) {

            }
        });
    }

    private void initInfoBox() {
        binding.infoLayout.body.setText(getString(R.string.show_the_visitors_this_qr_code_so_that_they_can_place_an_order_for_this_table));
    }

    private void handleBackButtonPressed() {
        getActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    private void initSeeDetailsButton() {
        binding.buttonSeeDetails.setOnClickListener(v -> {
            ((AddTableActivity) requireActivity()).openRestaurantTableDetailsActivity(tableId, locationId);
        });
    }
}