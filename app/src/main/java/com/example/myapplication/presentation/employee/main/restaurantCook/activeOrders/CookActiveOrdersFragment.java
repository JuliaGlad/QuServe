package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.VISITOR;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCookActiveOrdersBinding;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.recycler.ActiveOrdersAdapter;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.recycler.ActiveOrdersItemModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.state.CookActiveOrderStateModel;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.state.CookActiveOrdersState;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonModel;

import java.util.ArrayList;
import java.util.List;

public class CookActiveOrdersFragment extends Fragment {

    private CookActiveOrdersViewModel viewModel;
    private FragmentCookActiveOrdersBinding binding;
    private String restaurantId, locationId;
    private ActivityResultLauncher<Intent> launcher;
    private final List<ActiveOrdersItemModel> itemModels = new ArrayList<>();
    private final ActiveOrdersAdapter adapter = new ActiveOrdersAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CookActiveOrdersViewModel.class);
        restaurantId = requireActivity().getIntent().getStringExtra(COMPANY_ID);
        locationId = requireActivity().getIntent().getStringExtra(LOCATION_ID);
        viewModel.getOrders(restaurantId, locationId);
        initLauncher();
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
        initBackButton();
    }

    private void initLauncher() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String tableNumber = result.getData().getStringExtra(TABLE_NUMBER);
                        for (int i = 0; i < itemModels.size(); i++) {
                            if (Integer.parseInt(itemModels.get(i).getTableNumber()) == Integer.parseInt(tableNumber)){
                                itemModels.remove(i);
                                adapter.notifyItemRemoved(i);
                                if (itemModels.isEmpty()){
                                    initEmptyLayout();
                                }
                                break;
                            }
                        }
                    }
                });
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof CookActiveOrdersState.Success) {
                List<CookActiveOrderStateModel> models = ((CookActiveOrdersState.Success) state).data;
                if (!models.isEmpty()) {
                    initRecycler(models);
                } else {
                    binding.progressLayout.getRoot().setVisibility(View.GONE);
                    initEmptyLayout();
                }
            } else if (state instanceof CookActiveOrdersState.Loading) {
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof CookActiveOrdersState.Error) {
                setErrorLayout();
            }
        });
    }

    private void initEmptyLayout() {
        binding.emptyLayout.getRoot().setVisibility(View.VISIBLE);
        binding.emptyLayout.title.setText(getString(R.string.you_don_t_have_any_active_orders_yet));
        binding.emptyLayout.infoBox.body.setText(getString(R.string.view_available_orders_in_your_restaurant_and_select_them_and_then_come_back));
        binding.emptyLayout.buttonAdd.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setErrorLayout() {
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getOrders(restaurantId, locationId);
        });
    }

    private void initRecycler(List<CookActiveOrderStateModel> models) {
        for (int i = 0; i < models.size(); i++) {
            CookActiveOrderStateModel current = models.get(i);
            String tableNumber = current.getTableNumber();
            itemModels.add(new ActiveOrdersItemModel(i, tableNumber, current.getDishesCount(),
                    () -> {
                        ((CookActiveOrdersActivity)requireActivity()).openOrderDetailsActivity(current.getPath(), tableNumber, launcher);
                    }));
        }
        buildList(itemModels);
    }

    private void buildList(List<ActiveOrdersItemModel> models) {
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(models);
        binding.progressLayout.getRoot().setVisibility(View.GONE);
    }
}