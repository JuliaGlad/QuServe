package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentTableListBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.model.TableModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.recycler.TableListDelegate;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.recycler.TableListDelegateItem;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.recycler.TableListModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.state.TableListState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;

public class TableListFragment extends Fragment {

    private TableListViewModel viewModel;
    private FragmentTableListBinding binding;
    private String locationId, restaurantId;
    private Integer lastTableNumber = 0;
    private final MainAdapter adapter = new MainAdapter();
    private final List<DelegateItem> delegates = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TableListViewModel.class);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        locationId = requireActivity().getIntent().getStringExtra(LOCATION_ID);
        viewModel.getTables(restaurantId, locationId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTableListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setAdapter();
        initAddButton();
        initBackButton();
    }

    private void initBackButton() {
        requireActivity().finish();
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            if (lastTableNumber != null) {
                binding.buttonAdd.setEnabled(false);
                binding.loader.setVisibility(View.VISIBLE);
                viewModel.addTable(restaurantId, locationId, String.valueOf(lastTableNumber + 1));
            }
        });
    }

    private void setAdapter() {
        adapter.addDelegate(new AdviseBoxDelegate());
        adapter.addDelegate(new TableListDelegate());

        binding.recyclerView.setAdapter(adapter);
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof TableListState.Success) {
                List<TableModel> tables = ((TableListState.Success) state).data;
                if (!tables.isEmpty()) {
                    initRecycler(tables);
                } else {
                    binding.progressBar.getRoot().setVisibility(View.GONE);
                    binding.constraintLayout.setVisibility(View.VISIBLE);
                }
            } else if (state instanceof TableListState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof TableListState.Error) {
                binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
            }
        });
        viewModel.isAdded.observe(getViewLifecycleOwner(), tableId -> {
            if (tableId != null) {
                binding.constraintLayout.setVisibility(View.GONE);

                binding.buttonAdd.setEnabled(true);
                binding.loader.setVisibility(View.GONE);

                lastTableNumber += 1;
                int position = delegates.size();
                delegates.add(new TableListDelegateItem(new TableListModel(position, String.valueOf(lastTableNumber), () -> {
                    ((TableListActivity) requireActivity()).openRestaurantTableDetailsActivity(tableId, locationId);
                    requireActivity().finish();
                }
                )));
                if (position != 0) {
                    adapter.notifyItemInserted(position);
                } else {
                    adapter.submitList(delegates);
                }
            }
        });
    }

    private void initRecycler(List<TableModel> tables) {
        delegates.add(new AdviseBoxDelegateItem(new AdviseBoxModel(0, R.string.manage_your_restaurant_s_tables_and_view_active_orders)));
        tables.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getNumber())));
        if (!tables.isEmpty()) {
            for (int i = 0; i < tables.size(); i++) {
                String id = tables.get(i).getTableId();
                delegates.add(new TableListDelegateItem(new TableListModel(i, tables.get(i).getNumber(), () -> {
                    ((TableListActivity) requireActivity()).openRestaurantTableDetailsActivity(id, locationId);
                    requireActivity().finish();
                })));

            }
            lastTableNumber = tables.size();
        } else {
            lastTableNumber = 0;
        }


        adapter.submitList(delegates);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }
}