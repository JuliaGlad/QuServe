package com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAvailableCookOrdersBinding;
import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.recycler.AvailableOrdersAdapter;
import com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.recycler.AvailableOrdersModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.state.AvailableCookOrdersState;
import com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.state.AvailableOrdersStateModel;

import java.util.ArrayList;
import java.util.List;

public class AvailableCookOrdersFragment extends Fragment {

    private AvailableCookOrdersViewModel viewModel;
    private FragmentAvailableCookOrdersBinding binding;
    private String restaurantId, locationId;
    private List<AvailableOrdersModel> items = new ArrayList<>();
    private final AvailableOrdersAdapter adapter = new AvailableOrdersAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AvailableCookOrdersViewModel.class);
        restaurantId = getActivity().getIntent().getStringExtra(COMPANY_ID);
        locationId = getActivity().getIntent().getStringExtra(LOCATION_ID);
        viewModel.getOrders(restaurantId, locationId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAvailableCookOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initButtonBack();
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof AvailableCookOrdersState.Success) {
                List<AvailableOrdersStateModel> models = ((AvailableCookOrdersState.Success) state).data;
                if (!models.isEmpty()) {
                    initRecycler(models);
                } else {
                    binding.progressLayout.getRoot().setVisibility(View.GONE);
                    initEmptyOrders();
                }
            } else if (state instanceof AvailableCookOrdersState.Loading) {

            } else if (state instanceof AvailableCookOrdersState.Error) {
                setErrorLayout();
            }
        });

        viewModel.isTaken.observe(getViewLifecycleOwner(), index -> {
            if (index != null){
                int i = index.intValue();
                List<AvailableOrdersModel> newItems = new ArrayList<>(items);
                newItems.remove(i);
                adapter.submitList(newItems);
                items = newItems;
            }
        });
    }

    private void initEmptyOrders() {
        binding.emptyLayout.getRoot().setVisibility(View.VISIBLE);
        binding.emptyLayout.title.setText(getString(R.string.there_are_no_available_orders_yet));
        binding.emptyLayout.infoBox.body.setText(getString(R.string.wait_until_your_visitors_create_any_orders_and_then_come_back));
        binding.emptyLayout.buttonAdd.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setErrorLayout() {
        binding.errorLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getOrders(restaurantId, locationId);
        });
    }

    private void initRecycler(List<AvailableOrdersStateModel> models) {
        for (int i = 0; i < models.size(); i++) {
            AvailableOrdersStateModel current = models.get(i);
            String orderId = current.getOrderId();
            int index = i;
            items.add(new AvailableOrdersModel(index, current.getTableNumber(), current.getDishesCount(),
                    () -> {
                        viewModel.takeOrder(index, restaurantId, locationId, orderId);
                    },
                    () -> {
                        ((AvailableCookOrdersActivity)requireActivity()).openOrderDetailsActivity(current.getOrderPath());
                    }
            ));
        }
        buildList(items);
    }

    private void buildList(List<AvailableOrdersModel> items) {
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(items);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }
}