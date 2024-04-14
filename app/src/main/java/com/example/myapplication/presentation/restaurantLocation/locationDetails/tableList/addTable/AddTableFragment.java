package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_ID;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAddTableBinding;

public class AddTableFragment extends Fragment {

    private AddTableViewModel viewModel;
    private FragmentAddTableBinding binding;
    private String restaurantId, locationId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddTableViewModel.class);
        restaurantId = getActivity().getIntent().getStringExtra(COMPANY_ID);
        locationId = getActivity().getIntent().getStringExtra(LOCATION_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTableBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initAddButton();
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            String tableNumber = binding.editLayoutNumber.getText().toString();
            viewModel.addTable(restaurantId, locationId, tableNumber);
        });
    }

    private void setupObserves() {
        viewModel.isAdded.observe(getViewLifecycleOwner(), id -> {
            if (id != null){

                Bundle bundle = new Bundle();
                bundle.putString(TABLE_ID, id);
                bundle.putString(LOCATION_ID, locationId);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_addTableFragment_to_tableSuccessfullyAddedFragment, bundle);
            }
        });
    }
}