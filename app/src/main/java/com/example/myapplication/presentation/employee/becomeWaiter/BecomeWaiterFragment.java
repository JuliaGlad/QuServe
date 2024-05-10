package com.example.myapplication.presentation.employee.becomeWaiter;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_DATA;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBecomeWaiterBinding;
import com.example.myapplication.presentation.employee.becomeWaiter.state.BecomeWaiterModel;
import com.example.myapplication.presentation.employee.becomeWaiter.state.BecomeWaiterState;

public class BecomeWaiterFragment extends Fragment {

    private BecomeWaiterViewModel viewModel;
    private FragmentBecomeWaiterBinding binding;
    private String waiterPath, restaurantId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BecomeWaiterViewModel.class);
        waiterPath = getActivity().getIntent().getStringExtra(EMPLOYEE_DATA);
        viewModel.getRestaurantName(waiterPath);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBecomeWaiterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initButtonNo();
        initButtonYes();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel = null;
        binding = null;
    }

    private void initButtonYes() {
        binding.buttonYes.setOnClickListener(v -> {
            viewModel.addWaiter(waiterPath);
        });
    }

    private void initButtonNo() {
        binding.buttonNo.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof BecomeWaiterState.Success) {
                BecomeWaiterModel model = ((BecomeWaiterState.Success) state).data;
                restaurantId = model.getRestaurantId();
                binding.companyName.setText(model.getName());
                Glide.with(requireView())
                        .load(model.getUri())
                        .into(binding.qrCodeImage);

            } else if (state instanceof BecomeWaiterState.Loading) {

            } else if (state instanceof BecomeWaiterState.Error) {

            }
        });

        viewModel.isAdded.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, restaurantId);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_becomeWaiterFragment_to_successfullyBecomeWaiter, bundle);
            }
        });
    }
}