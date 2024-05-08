package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.dishDetails;

import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentDishDetailsWithIndicatorsBinding;
import com.example.myapplication.presentation.common.recycler.OrderDetailsAdapter;
import com.example.myapplication.presentation.common.recycler.OrderDetailsItemModel;
import com.example.myapplication.presentation.common.state.OrderDetailsState;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.dishDetails.recycler.OrderDetailsWithIndicatorsAdapter;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.dishDetails.recycler.OrderDetailsWithIndicatorsItemModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.dishDetails.state.OrderDetailsWithIndicatorsModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.dishDetails.state.OrderDetailsWithIndicatorsState;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.dishDetails.state.OrderDetailsWithIndicatorsStateModel;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailsWithIndicatorsFragment extends Fragment {

    private OrderDetailsWithIndicatorsViewModel viewModel;
    private String path, tableNumber, restaurantId;
    List<OrderDetailsWithIndicatorsItemModel> items = new ArrayList<>();
    private FragmentDishDetailsWithIndicatorsBinding binding;
    private final OrderDetailsWithIndicatorsAdapter adapter = new OrderDetailsWithIndicatorsAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderDetailsWithIndicatorsViewModel.class);
        path = getActivity().getIntent().getStringExtra(PATH);
        tableNumber = getActivity().getIntent().getStringExtra(TABLE_NUMBER);
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
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof OrderDetailsWithIndicatorsState.Success){
                OrderDetailsWithIndicatorsStateModel model = ((OrderDetailsWithIndicatorsState.Success) state).data;
                restaurantId = model.getRestaurantId();
                binding.totalPrice.setText(model.getTotalPrice());
                initRecycler(model.getModels());

            } else if (state instanceof OrderDetailsWithIndicatorsState.Loading){

            } else if (state instanceof OrderDetailsState.Error){

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

        viewModel.isMove.observe(getViewLifecycleOwner(), aBoolean -> {

        });
    }

    private void removeItemFromList(int index) {
        List<OrderDetailsWithIndicatorsItemModel> newItems = new ArrayList<>(items);
        newItems.remove(index);
        adapter.submitList(newItems);
        items = newItems;
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
                        viewModel.addToReadyDishes(index, current.getDocumentDishId(), tableNumber, dishName, path);
                    }
            ));
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(items);
    }
}