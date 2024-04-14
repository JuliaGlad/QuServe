package com.example.myapplication.presentation.service.main.restaurantService;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentServiceRestaurantBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.service.main.restaurantService.recycler.ServiceRestaurantButtonAdapter;
import com.example.myapplication.presentation.service.main.restaurantService.recycler.ServiceRestaurantButtonModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceRestaurant extends Fragment {

    private FragmentServiceRestaurantBinding binding;
    private final List<ServiceRestaurantButtonModel> list = new ArrayList<>();
    private final ServiceRestaurantButtonAdapter adapter = new ServiceRestaurantButtonAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentServiceRestaurantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler() {
        buildList(new ServiceRestaurantButtonModel[]{
                new ServiceRestaurantButtonModel(1, R.drawable.menu_background, getString(R.string.restaurant_menu), () -> {

                }),
                new ServiceRestaurantButtonModel(2, R.drawable.location_background, getString(R.string.locations), () -> {
                    ((MainActivity)requireActivity()).openRestaurantLocationsActivity();
                })
        });
    }

    private void buildList(ServiceRestaurantButtonModel[] models) {
        list.addAll(Arrays.asList(models));
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(list);
    }
}
