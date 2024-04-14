package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentTableListBinding;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.RestaurantLocationDetailsActivity;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.model.TableModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.recycler.TableListDelegate;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.recycler.TableListDelegateItem;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.recycler.TableListModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.state.TableListState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListDelegate;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListModel;

public class TableListFragment extends Fragment {

    private TableListViewModel viewModel;
    private FragmentTableListBinding binding;
    private String locationId, restaurantId;
    private final MainAdapter adapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TableListViewModel.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        locationId = getActivity().getIntent().getStringExtra(LOCATION_ID);
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
    }

    private void initAddButton() {
        binding.buttonAdd.setOnClickListener(v -> {
            ((TableListActivity)requireActivity()).openAddTableActivity(locationId, restaurantId);
        });
    }

    private void setAdapter() {
        adapter.addDelegate(new AdviseBoxDelegate());
        adapter.addDelegate(new TableListDelegate());

        binding.recyclerView.setAdapter(adapter);
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof TableListState.Success){
                List<TableModel> tables = ((TableListState.Success)state).data;
                initRecycler(tables);
            } else if (state instanceof TableListState.Loading){

            } else if (state instanceof TableListState.Error){

            }
        });
    }

    private void initRecycler(List<TableModel> tables) {
        List<DelegateItem> delegates = new ArrayList<>();
        delegates.add(new AdviseBoxDelegateItem(new AdviseBoxModel(0, R.string.manage_your_restaurant_s_tables_and_view_active_orders)));
        for (int i = 0; i < tables.size(); i++) {
            String id = tables.get(i).getTableId();
            delegates.add(new TableListDelegateItem(new TableListModel(i + 1, tables.get(i).getNumber(), () -> {
                ((TableListActivity)requireActivity()).openRestaurantTableDetailsActivity(id, locationId);
                requireActivity().finish();
            })));
        }
        adapter.submitList(delegates);
    }
}