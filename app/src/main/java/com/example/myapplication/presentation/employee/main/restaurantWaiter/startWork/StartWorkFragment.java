package com.example.myapplication.presentation.employee.main.restaurantWaiter.startWork;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_NAME;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentStartWorkBinding;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.MainWaiterFragment;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.startWork.state.StartWorkState;

public class StartWorkFragment extends Fragment {

    private StartWorkViewModel viewModel;
    private FragmentStartWorkBinding binding;
    private String restaurantId, locationId, restaurantName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StartWorkViewModel.class);
        restaurantId = getArguments().getString(COMPANY_ID);
        locationId = getArguments().getString(LOCATION_ID);
        viewModel.getRestaurantName(restaurantId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStartWorkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initInfoLayout();
        initButtonWork();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }

    private void setupObserves() {
        viewModel.name.observe(getViewLifecycleOwner(), name -> {
            if (name instanceof StartWorkState.Success){
                restaurantName = ((StartWorkState.Success)name).data;
                binding.restaurantName.setText(restaurantName);
                binding.progressBar.getRoot().setVisibility(View.GONE);
            } else if (name instanceof StartWorkState.Loading){
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (name instanceof StartWorkState.Error){
                binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
            }
        });

        viewModel.isStarted.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                Bundle bundle = new Bundle();
                bundle.putString(LOCATION_ID, locationId);
                bundle.putString(COMPANY_ID, restaurantId);
                bundle.putString(RESTAURANT_NAME, restaurantName);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                        .replace(R.id.employee_nav_container, MainWaiterFragment.class, bundle)
                        .commit();
            }
        });
    }

    private void initButtonWork() {
        binding.buttonStartWorking.setOnClickListener(v -> {
            viewModel.startWorking(restaurantId, locationId);
        });
    }

    private void initInfoLayout() {
        binding.infoBoxLayout.body.setText(getString(R.string.start_work_to_get_orders_you_have_to_serve));
    }
}