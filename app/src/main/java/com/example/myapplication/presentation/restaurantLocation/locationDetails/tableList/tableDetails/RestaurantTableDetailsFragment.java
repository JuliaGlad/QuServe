package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails;

import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_ID;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRestaurantTableDetailsBinding;
import com.example.myapplication.presentation.dialogFragments.deleteTable.DeleteTableDialogFragment;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.model.TableDetailModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.state.TableDetailsState;

import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegate;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionModel;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegate;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewModel;

public class RestaurantTableDetailsFragment extends Fragment {

    private RestaurantTableDetailsViewModel viewModel;
    private FragmentRestaurantTableDetailsBinding binding;
    private final MainAdapter adapter = new MainAdapter();
    private String restaurantId, locationId, tableId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        restaurantId = sharedPreferences.getString(COMPANY_ID, null);
        locationId = getActivity().getIntent().getStringExtra(LOCATION_ID);
        tableId = getActivity().getIntent().getStringExtra(TABLE_ID);
        viewModel = new ViewModelProvider(this).get(RestaurantTableDetailsViewModel.class);
        viewModel.getTableData(restaurantId, locationId, tableId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantTableDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setAdapter();
        initMenu();
    }

    private void setAdapter() {
        adapter.addDelegate(new ImageViewDelegate());
        adapter.addDelegate(new ButtonWithDescriptionDelegate());
    }

    private void initMenu() {
        binding.buttonMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.table_details_menu, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
                showDeleteTableDialog();
                return false;
            });
        });
    }

    private void showDeleteTableDialog() {
        DeleteTableDialogFragment dialogFragment = new DeleteTableDialogFragment(tableId, restaurantId, locationId);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "DELETE_TABLE_DIALOG");
        dialogFragment.onDialogDismissedListener(bundle -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getActivity(), state -> {
            if (state instanceof TableDetailsState.Success){
                TableDetailModel model = ((TableDetailsState.Success)state).data;
                binding.tableNumber.setText(model.getNumber());
                initRecycler(model.getQrCode());
            } else if (state instanceof TableDetailsState.Loading){

            } else if (state instanceof TableDetailsState.Error){

            }
        });

        viewModel.pdfUri.observe(getActivity(), uri -> {
            if (!uri.equals(Uri.EMPTY)){
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                requireActivity().startActivity(intent);
            }
        });
    }

    private void initRecycler(Uri uri) {
        buildList(new DelegateItem[]{
                new ImageViewDelegateItem(new ImageViewModel(1, uri)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, R.string.download_pdf, R.string.show_the_visitors_this_qr_code_so_that_they_can_place_an_order_for_this_table,R.drawable.qr_code,
                        () -> {
                            viewModel.getQrCodePdf(tableId);
                        })),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(3, R.string.view_order, R.string.view_details_of_active_order_on_this_table,R.drawable.ic_list_fill,
                        () -> {

                        }))
        });
    }

    private void buildList(DelegateItem[] delegates){
        List<DelegateItem> items = Arrays.asList(delegates);
        adapter.submitList(items);
    }
}