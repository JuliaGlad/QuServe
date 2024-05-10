package com.example.myapplication.presentation.common.orderDetails;

import static com.example.myapplication.presentation.utils.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.VISITOR;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentOrderDetailsBinding;
import com.example.myapplication.presentation.common.recycler.OrderDetailsAdapter;
import com.example.myapplication.presentation.common.recycler.OrderDetailsItemModel;
import com.example.myapplication.presentation.common.state.OrderDetailsDishModel;
import com.example.myapplication.presentation.common.state.OrderDetailsState;
import com.example.myapplication.presentation.common.state.OrderDetailsStateModel;
import com.example.myapplication.presentation.dialogFragments.finishRestaurantOrder.FinishRestaurantOrderDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsFragment extends Fragment {

    private OrderDetailsViewModel viewModel;
    private FragmentOrderDetailsBinding binding;
    private String state, path;
    private final OrderDetailsAdapter adapter = new OrderDetailsAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderDetailsViewModel.class);
        path = getActivity().getIntent().getStringExtra(PATH);
        state = getActivity().getIntent().getStringExtra(STATE);
        viewModel.getOrder(path);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initButtonBack();
        if (state.equals(VISITOR)){
            initMenu();
        }
    }

    private void initMenu() {
        binding.buttonMenu.setVisibility(View.VISIBLE);
        binding.buttonMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.finish_restaurant_order_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
                showFinishOrderDialog();
                return false;
            });
        });
    }

    private void showFinishOrderDialog() {
        FinishRestaurantOrderDialogFragment dialogFragment = new FinishRestaurantOrderDialogFragment(path);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "FINISH_RESTAURANT_ORDER_DIALOG");
        dialogFragment.onDialogDismissedListener(bundle -> {
            requireActivity().finish();
        });
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof OrderDetailsState.Success){
                OrderDetailsStateModel model = ((OrderDetailsState.Success)state).data;
                binding.totalPrice.setText(model.getTotalPrice());
                initRecycler(model.getModels());

            } else if (state instanceof OrderDetailsState.Loading){

            } else if (state instanceof OrderDetailsState.Error){

            }
        });
    }

    private void initRecycler(List<OrderDetailsDishModel> dishes) {
        List<OrderDetailsItemModel> items = new ArrayList<>();
        for (int i = 0; i < dishes.size(); i++) {
            OrderDetailsDishModel current = dishes.get(i);
            items.add(new OrderDetailsItemModel(
                    i,
                    current.getName(),
                    current.getPrice(),
                    current.getWeight(),
                    current.getAmount(),
                    current.getTask(),
                    current.getRequiredChoice(),
                    current.getTopping(),
                    current.getToRemove()
            ));
        }
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(items);
    }
}