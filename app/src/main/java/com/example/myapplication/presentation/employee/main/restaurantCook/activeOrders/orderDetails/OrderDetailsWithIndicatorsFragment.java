package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails;

import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDishDetailsWithIndicatorsBinding;
import com.example.myapplication.presentation.common.orderDetails.state.OrderDetailsState;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.recycler.OrderDetailsWithIndicatorsAdapter;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.recycler.OrderDetailsWithIndicatorsItemModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.state.OrderDetailsWithIndicatorsModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.state.OrderDetailsWithIndicatorsState;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.state.OrderDetailsWithIndicatorsStateModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsWithIndicatorsFragment extends Fragment {

    private OrderDetailsWithIndicatorsViewModel viewModel;
    private String path;
    private String tableNumber;
    List<OrderDetailsWithIndicatorsItemModel> items = new ArrayList<>();
    private FragmentDishDetailsWithIndicatorsBinding binding;
    private final OrderDetailsWithIndicatorsAdapter adapter = new OrderDetailsWithIndicatorsAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderDetailsWithIndicatorsViewModel.class);
        path = requireActivity().getIntent().getStringExtra(PATH);
        tableNumber = requireActivity().getIntent().getStringExtra(TABLE_NUMBER);
        viewModel.getOrder(path);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDishDetailsWithIndicatorsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initBackButton();
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> requireActivity().finish());
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof OrderDetailsWithIndicatorsState.Success){
                OrderDetailsWithIndicatorsStateModel model = ((OrderDetailsWithIndicatorsState.Success) state).data;
                binding.totalPrice.setText(model.getTotalPrice());
                binding.number.setText(tableNumber);
                initRecycler(model.getModels());

            } else if (state instanceof OrderDetailsWithIndicatorsState.Loading){
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof OrderDetailsState.Error){
                setErrorLayout();
            } else if (state instanceof OrderDetailsWithIndicatorsState.OrderIsFinished){
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_orderDetailsWithIndicatorsFragment_to_cookOrderIsFinishedFragment);
            }
        });

        viewModel.isReady.observe(getViewLifecycleOwner(), index -> {
            if (index != null){
                removeItemFromList(index);
            }
        });

        viewModel.isFinished.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                viewModel.finishOrder(path);
            }
        });
    }

    private void setErrorLayout() {
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getOrder(path);
        });
    }

    private void removeItemFromList(int index) {
        items.remove(index);
        adapter.notifyItemRemoved(index);
    }

    private void initRecycler(List<OrderDetailsWithIndicatorsModel> dishes) {
        for (int i = 0; i < dishes.size(); i++) {
            OrderDetailsWithIndicatorsModel current = dishes.get(i);
            int index = i;
            String dishName = current.getName();
            items.add(new OrderDetailsWithIndicatorsItemModel(
                    index,
                    dishName,
                    current.getPrice(),
                    current.getWeight(),
                    current.getAmount(),
                    current.getTask(),
                    current.getRequiredChoice(),
                    current.getTopping(),
                    current.getToRemove(),
                    () -> {
                        int position = 0;
                        for (int j = 0; j < items.size(); j++) {
                            OrderDetailsWithIndicatorsItemModel currentModel = items.get(j);
                            if (index == currentModel.getId()){
                                position = j;
                            }
                        }

                        viewModel.addToReadyDishes(position, current.getDocumentDishId(), tableNumber, dishName, current.getAmount(), path);
                    }
            ));
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(items);
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }
}