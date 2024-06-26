package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ORDER_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;

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
import com.example.myapplication.databinding.FragmentTableOrderDetailsBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.recycler.DishTableDetailsAdapter;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.recycler.DishTableDetailsItemModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.state.TableDetailsDishModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.state.TableOrderDetailsState;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.orderDetails.state.TableOrderDetailsStateModel;

import java.util.ArrayList;
import java.util.List;

public class TableOrderDetailsFragment extends Fragment {

    private TableOrderDetailsViewModel viewModel;
    private FragmentTableOrderDetailsBinding binding;
    private final DishTableDetailsAdapter adapter = new DishTableDetailsAdapter();
    private String tableNumber, orderId, locationId, restaurantId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TableOrderDetailsViewModel.class);
        orderId = requireArguments().getString(ORDER_ID);
        locationId = requireArguments().getString(LOCATION_ID);
        restaurantId = requireArguments().getString(COMPANY_ID);
        tableNumber = requireArguments().getString(TABLE_NUMBER);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTableOrderDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initButtonBack();
        if (orderId != null) {
            viewModel.getTableOrderData(restaurantId, locationId, orderId);
        } else {
            binding.progressBar.getRoot().setVisibility(View.GONE);
            initEmptyLayout();
        }
    }

    private void initButtonBack() {
        binding.buttonBack.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
    }

    private void initEmptyLayout() {
        binding.emptyLayout.getRoot().setVisibility(View.VISIBLE);
        binding.emptyLayout.title.setText(getString(R.string.there_is_no_order_on_this_table_yet));
        binding.emptyLayout.buttonAdd.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
        binding.emptyLayout.infoBox.body.setText(getString(R.string.wait_until_anybody_creates_an_order_and_then_come_back));
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof TableOrderDetailsState.Success) {
                TableOrderDetailsStateModel model = ((TableOrderDetailsState.Success) state).data;
                if (model != null) {
                    binding.cookName.setText(model.getCookName());
                    binding.waiterName.setText(model.getWaiterName());
                    binding.number.setText(tableNumber);
                    initRecycler(model.getDishes());
                } else {
                    binding.errorLayout.getRoot().setVisibility(View.GONE);
                    binding.progressBar.getRoot().setVisibility(View.GONE);
                    initEmpty();
                }

            } else if (state instanceof TableOrderDetailsState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof TableOrderDetailsState.Error) {
                setErrorLayout();
            }
        });
    }

    private void initEmpty() {
        binding.emptyLayout.getRoot().setVisibility(View.VISIBLE);
        binding.emptyLayout.title.setText(getString(R.string.there_is_no_order_on_this_table_yet));
        binding.emptyLayout.infoBox.body.setText(getString(R.string.wait_until_anybody_creates_an_order_and_then_come_back));
        binding.emptyLayout.buttonAdd.setVisibility(View.GONE);
    }

    private void setErrorLayout() {
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> viewModel.getTableOrderData(restaurantId, locationId, orderId));
    }

    private void initRecycler(List<TableDetailsDishModel> models) {
        binding.recyclerView.setAdapter(adapter);
        List<DishTableDetailsItemModel> items = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            TableDetailsDishModel current = models.get(i);
            items.add(new DishTableDetailsItemModel(i, current.getName(), current.getCount()));
        }
        adapter.submitList(items);
        binding.emptyLayout.getRoot().setVisibility(View.GONE);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }
}