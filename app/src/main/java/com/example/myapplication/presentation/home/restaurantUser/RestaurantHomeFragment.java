package com.example.myapplication.presentation.home.restaurantUser;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRestaurantHomeBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeRestaurantLocationButton.HomeRestaurantLocationDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeRestaurantLocationButton.HomeRestaurantLocationDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeRestaurantLocationButton.HomeRestaurantLocationModel;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonModel;
import com.example.myapplication.presentation.home.restaurantUser.state.RestaurantHomeLocationModel;
import com.example.myapplication.presentation.home.restaurantUser.state.RestaurantHomeState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegate;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionModel;

public class RestaurantHomeFragment extends Fragment {

    private RestaurantHomeViewModel viewModel;
    private FragmentRestaurantHomeBinding binding;
    private String restaurantId;
    private final MainAdapter adapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RestaurantHomeViewModel.class);
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPref.getString(COMPANY_ID, null);
        viewModel.getRestaurantLocations(restaurantId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof RestaurantHomeState.Success) {
                List<RestaurantHomeLocationModel> models = ((RestaurantHomeState.Success) state).data;
                initRecycler(models);

            } else if (state instanceof RestaurantHomeState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof RestaurantHomeState.Error) {
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> viewModel.getRestaurantLocations(restaurantId));
    }

    private void initRecycler(List<RestaurantHomeLocationModel> models) {
        if (models.isEmpty()) {
            initEmptyActionRecycler();
        } else {
            List<DelegateItem> delegates = new ArrayList<>();
            delegates.add(new SquareButtonDelegateItem(new SquareButtonModel(1, R.string.add_location, R.string.open_menu, R.drawable.ic_add_location, R.drawable.ic_menu_book,
                    () -> {
                        ((MainActivity)requireActivity()).openAddLocationsActivity();
                    },
                    () -> {
                        ((MainActivity)requireActivity()).openRestaurantMenuActivity();
                    })));
            delegates.addAll(addLocations(models));
            adapter.addDelegate(new SquareButtonDelegate());
            adapter.addDelegate(new HomeRestaurantLocationDelegate());
            binding.recyclerView.setAdapter(adapter);
            adapter.submitList(delegates);
            binding.progressBar.getRoot().setVisibility(View.GONE);
            binding.errorLayout.errorLayout.setVisibility(View.GONE);
        }
    }

    private List<HomeRestaurantLocationDelegateItem> addLocations(List<RestaurantHomeLocationModel> models) {
        List<HomeRestaurantLocationDelegateItem> delegates = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            RestaurantHomeLocationModel current = models.get(i);
            delegates.add(new HomeRestaurantLocationDelegateItem(new HomeRestaurantLocationModel(
                    i,
                    current.getLocation(),
                    current.getActiveOrders(),
                    () -> ((MainActivity)requireActivity()).openLocationDetailsActivity(current.getLocationId())
            )));
        }
        return delegates;
    }

    private void initEmptyActionRecycler() {
        buildList(new DelegateItem[]{
                new AdviseBoxDelegateItem(new AdviseBoxModel(1, R.string.here_you_will_see_all_your_available_actions)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.add_location), getString(R.string.create_new_restaurant_location_add_tables_and_employees_and_take_orders), R.drawable.ic_add_location, () -> ((MainActivity) requireActivity()).openAddLocationsActivity())),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.open_menu), getString(R.string.open_and_edit_restaurant_menu_so_your_visitors_can_use_it), R.drawable.ic_menu_book,
                        () -> ((MainActivity) requireActivity()).openRestaurantMenuActivity()))
        });
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = Arrays.asList(items);
        adapter.addDelegate(new AdviseBoxDelegate());
        adapter.addDelegate(new ButtonWithDescriptionDelegate());
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(list);
    }
}