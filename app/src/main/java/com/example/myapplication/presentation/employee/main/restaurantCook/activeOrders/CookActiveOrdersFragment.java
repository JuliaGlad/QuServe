package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentCookActiveOrdersBinding;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.recycler.ActiveOrdersAdapter;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.recycler.ActiveOrdersItemModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.state.CookActiveOrderStateModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.state.CookActiveOrdersState;

import java.util.ArrayList;
import java.util.List;

public class CookActiveOrdersFragment extends Fragment {

    private CookActiveOrdersViewModel viewModel;
    private FragmentCookActiveOrdersBinding binding;
    private String restaurantId, locationId;
    private final ActiveOrdersAdapter adapter = new ActiveOrdersAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CookActiveOrdersViewModel.class);
        restaurantId = getActivity().getIntent().getStringExtra(COMPANY_ID);
        locationId = getActivity().getIntent().getStringExtra(LOCATION_ID);
        viewModel.getOrders(restaurantId, locationId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCookActiveOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof CookActiveOrdersState.Success) {
                List<CookActiveOrderStateModel> models = ((CookActiveOrdersState.Success) state).data;
                initRecycler(models);

            } else if (state instanceof CookActiveOrdersState.Loading) {

            } else if (state instanceof CookActiveOrdersState.Error) {
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getOrders(restaurantId, locationId);
        });
    }

    private void initRecycler(List<CookActiveOrderStateModel> models) {
        List<ActiveOrdersItemModel> itemModels = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            CookActiveOrderStateModel current = models.get(i);
            String tableNumber = current.getTableNumber();
            itemModels.add(new ActiveOrdersItemModel(i, tableNumber, current.getDishesCount(),
                    () -> {
                        ((CookActiveOrdersActivity)requireActivity()).openOrderDetailsActivity(current.getPath(), tableNumber);
                    }));
        }
        buildList(itemModels);
    }

    private void buildList(List<ActiveOrdersItemModel> models) {
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(models);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }
}