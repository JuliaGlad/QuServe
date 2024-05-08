package com.example.myapplication.presentation.employee.main.restaurantCook;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCookEmployeeBinding;
import com.example.myapplication.presentation.MainActivity;

public class CookEmployeeFragment extends Fragment {

    private FragmentCookEmployeeBinding binding;
    private String locationId, restaurantId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCookEmployeeBinding.inflate(inflater, container, false);
        locationId = getArguments().getString(LOCATION_ID);
        restaurantId =  getArguments().getString(COMPANY_ID);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initYourOrdersButton();
        initAvailableOrdersButton();
    }

    private void initYourOrdersButton() {
        binding.buttonYourOrders.icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_order_approve, getActivity().getTheme()));
        binding.buttonYourOrders.description.setText(getString(R.string.view_your_taken_orders_and_track_their_fulfillment));
        binding.buttonYourOrders.headLine.setText(getString(R.string.your_orders));
        binding.buttonYourOrders.item.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openCookActiveOrdersActivity(restaurantId, locationId);
        });
    }

    private void initAvailableOrdersButton() {
        binding.buttonAvailableOrders.icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_add_queue, getActivity().getTheme()));
        binding.buttonAvailableOrders.description.setText(getString(R.string.view_available_orders_in_your_restaurant_and_accept_them));
        binding.buttonAvailableOrders.headLine.setText(getString(R.string.available_orders));
        binding.buttonAvailableOrders.item.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openAvailableOrdersActivity(restaurantId, locationId);
        });
    }
}
