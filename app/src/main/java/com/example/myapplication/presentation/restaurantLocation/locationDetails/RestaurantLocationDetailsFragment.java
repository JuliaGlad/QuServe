package com.example.myapplication.presentation.restaurantLocation.locationDetails;

import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRestaurantLocationDetailsBinding;
import com.example.myapplication.presentation.dialogFragments.cookQrCode.CookQrCodeDialogFragment;
import com.example.myapplication.presentation.dialogFragments.waiterQrCode.WaiterQrCodeDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.ui.items.items.optionImageButton.OptionImageButtonAdapter;
import myapplication.android.ui.recycler.ui.items.items.optionImageButton.OptionImageButtonModel;

public class RestaurantLocationDetailsFragment extends Fragment {

    private String locationId;
    private RestaurantLocationDetailsViewModel viewModel;
    private FragmentRestaurantLocationDetailsBinding binding;
    private final OptionImageButtonAdapter adapter = new OptionImageButtonAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RestaurantLocationDetailsViewModel.class);
        locationId = requireActivity().getIntent().getStringExtra(LOCATION_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantLocationDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initRecycler();
        initBackButton();
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void initRecycler() {
        buildList(new OptionImageButtonModel[]{
                new OptionImageButtonModel(1, R.drawable.table_list_background, getResources().getString(R.string.table_list), () -> {
                    ((RestaurantLocationDetailsActivity)requireActivity()).openTableListActivity(locationId);
                }),
                new OptionImageButtonModel(2, R.drawable.add_cooker_background, getResources().getString(R.string.add_cooker), () -> {
                    viewModel.getCookQrCode(locationId);
                }),
                new OptionImageButtonModel(3, R.drawable.restaurant_action_background, getResources().getString(R.string.add_waiter), () -> {
                    viewModel.getWaiterQrCode(locationId);
                })
        });
    }

    private void setupObserves(){
        viewModel.cookQrCode.observe(getViewLifecycleOwner(), uri -> {
            if (!uri.equals(Uri.EMPTY)){
                openCookQrCodeDialog(uri);
            }
        });

        viewModel.waiterQrCode.observe(getViewLifecycleOwner(), uri -> {
            if (!uri.equals(Uri.EMPTY)){
                openWaiterQrCodeDialog(uri);
            }
        });
    }

    private void openWaiterQrCodeDialog(Uri uri){
        WaiterQrCodeDialogFragment dialogFragment = new WaiterQrCodeDialogFragment(uri);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "COOK_QR_CODE_DIALOG");
    }

    private void openCookQrCodeDialog(Uri uri){
        CookQrCodeDialogFragment dialogFragment = new CookQrCodeDialogFragment(uri);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "COOK_QR_CODE_DIALOG");
    }

    private void buildList(OptionImageButtonModel[] models) {
        List<OptionImageButtonModel> list = new ArrayList<>(Arrays.asList(models));
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(list);
    }
}