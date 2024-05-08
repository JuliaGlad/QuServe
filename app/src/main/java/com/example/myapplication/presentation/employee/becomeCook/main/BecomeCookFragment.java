package com.example.myapplication.presentation.employee.becomeCook.main;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_DATA;
import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBecomeCookBinding;
import com.example.myapplication.presentation.employee.becomeCook.state.BecomeCookState;
import com.example.myapplication.presentation.employee.becomeCook.state.BecomeCookStateModel;

public class BecomeCookFragment extends Fragment {

    private BecomeCookViewModel viewModel;
    private FragmentBecomeCookBinding binding;
    private String path, restaurantId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BecomeCookViewModel.class);
        path = getActivity().getIntent().getStringExtra(EMPLOYEE_DATA);
        viewModel.getData(path);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBecomeCookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initYesButton();
        initNoButton();
    }

    private void initNoButton() {
        binding.buttonNo.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void initYesButton() {
        binding.buttonYes.setOnClickListener(v -> {
            viewModel.addCook(path);
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof BecomeCookState.Success){

                BecomeCookStateModel model = ((BecomeCookState.Success)state).data;
                restaurantId = model.getRestaurantId();
                binding.companyName.setText(model.getName());

                Glide.with(requireView())
                        .load(model.getUri())
                        .into(binding.qrCodeImage);

            } else if (state instanceof BecomeCookState.Loading){

            } else if (state instanceof BecomeCookState.Error){

            }
        });

        viewModel.isComplete.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){

                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, restaurantId);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_becomeCookFragment_to_successfullyBecomeCookFragment);
            }
        });

    }
}