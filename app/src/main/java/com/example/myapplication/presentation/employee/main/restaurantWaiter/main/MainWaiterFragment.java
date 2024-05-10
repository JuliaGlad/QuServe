package com.example.myapplication.presentation.employee.main.restaurantWaiter.main;

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
import android.widget.PopupMenu;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMainWaiterBinding;
import com.example.myapplication.presentation.dialogFragments.cannotStopWaiterWork.CannotStopWaiterWorkDialogFragment;
import com.example.myapplication.presentation.dialogFragments.stopWaiterWork.StopWaiterWorkDialogFragment;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.recycler.WaiterItemAdapter;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.recycler.WaiterItemModel;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.state.MainWaiterState;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.state.MainWaiterStateModel;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.state.WaiterReadyDishesModel;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.startWork.StartWorkFragment;

import java.util.ArrayList;
import java.util.List;

public class MainWaiterFragment extends Fragment {

    private MainWaiterViewModel viewModel;
    private FragmentMainWaiterBinding binding;
    private WaiterItemAdapter adapter = new WaiterItemAdapter();
    private List<WaiterItemModel> items = new ArrayList<>();
    private String restaurantId, locationId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainWaiterViewModel.class);
        restaurantId = getArguments().getString(COMPANY_ID);
        locationId = getArguments().getString(LOCATION_ID);
        viewModel.checkIsWorking(restaurantId, locationId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainWaiterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setupObserves();
        initMenu();
    }

    private void setAdapter() {
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }

    private void initMenu() {
        binding.buttonMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.waiter_stop_work_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
                if (items.size() == 0) {
                    StopWaiterWorkDialogFragment dialogFragment = new StopWaiterWorkDialogFragment(restaurantId, locationId);
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "STOP_WAITER_WORK_DIALOG");
                    dialogFragment.onDialogDismissedListener(bundle -> {
                        navigateToStartWorking();
                    });
                } else {
                    CannotStopWaiterWorkDialogFragment dialogFragment = new CannotStopWaiterWorkDialogFragment();
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "CANNOT_STOP_WAITER_WORK_DIALOG");
                }
                return false;
            });
        });
    }

    private void navigateToStartWorking() {
        Bundle bundle = new Bundle();
        bundle.putString(LOCATION_ID, locationId);
        bundle.putString(COMPANY_ID, restaurantId);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.employee_nav_container, StartWorkFragment.class, bundle)
                .commit();
    }

    private void setupObserves() {

        viewModel.isWorking.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null && aBoolean) {
                viewModel.getOrders(restaurantId, locationId);
            } else if (aBoolean != null) {
                navigateToStartWorking();
            }
        });

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof MainWaiterState.Success) {
                MainWaiterStateModel model = ((MainWaiterState.Success) state).data;
                binding.restaurantName.setText(model.getRestaurantName());
                initRecycler(model.getDishes());

            } else if (state instanceof MainWaiterState.Loading) {

            } else if (state instanceof MainWaiterState.Error) {

            }
        });

        viewModel.added.observe(getViewLifecycleOwner(), mainWaiterStateModel -> {
            if (mainWaiterStateModel != null) {
                int index = items.size();
                addWaiterItem(items, index, mainWaiterStateModel);
                adapter.notifyItemInserted(index);
            }
        });

        viewModel.removed.observe(getViewLifecycleOwner(), index -> {
            if (index != null) {
                items.remove(index.intValue());
                adapter.notifyItemRemoved(index.intValue());
            }
        });
    }

    private void initRecycler(List<WaiterReadyDishesModel> models) {
        for (int i = 0; i < models.size(); i++) {
            WaiterReadyDishesModel current = models.get(i);
            addWaiterItem(items, i, current);
        }
        adapter.submitList(items);
    }

    private void addWaiterItem(List<WaiterItemModel> items, int index, WaiterReadyDishesModel mainWaiterStateModel) {
        items.add(new WaiterItemModel(
                index,
                mainWaiterStateModel.getTableNumber(),
                mainWaiterStateModel.getDishName(),
                mainWaiterStateModel.getDishCount(),
                () -> {
                    int currentPosition = 0;
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).getId() == index) {
                            currentPosition = i;
                        }
                    }
                    viewModel.onDishServed(currentPosition, restaurantId, locationId, mainWaiterStateModel.getOrderDocId());
                }
        ));
    }

}