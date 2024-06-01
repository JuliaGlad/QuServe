package com.example.myapplication.presentation.service.basicUser;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_DATA;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBasicUserServiceBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.dialogFragments.alreadyHaveOrder.AlreadyHaveOrderDialogFragment;
import com.example.myapplication.presentation.dialogFragments.needAccountDialog.NeedAccountDialogFragment;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.QueueAdminFragment;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.QueueWorkerFragment;
import com.example.myapplication.presentation.employee.main.restaurantCook.CookEmployeeFragment;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.main.MainWaiterFragment;
import com.example.myapplication.presentation.restaurantOrder.menu.RestaurantOrderMenuActivity;
import com.example.myapplication.presentation.service.ScanCode;
import com.example.myapplication.presentation.service.basicUser.becomeEmployeeOptions.BecomeEmployeeOptionsActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.ui.items.items.optionImageButton.OptionImageButtonAdapter;
import myapplication.android.ui.recycler.ui.items.items.optionImageButton.OptionImageButtonModel;

public class BasicUserServiceFragment extends Fragment {

    private BasicUserServiceViewModel viewModel;
    private FragmentBasicUserServiceBinding binding;
    private ActivityResultLauncher<ScanOptions> restaurantLauncher;
    private final List<OptionImageButtonModel> list = new ArrayList<>();
    private final OptionImageButtonAdapter adapter = new OptionImageButtonAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BasicUserServiceViewModel.class);
        initRestaurantLauncher();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBasicUserServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initRecycler();
    }

    private void setupObserves() {
        viewModel.isVisitor.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null){
                if (aBoolean){
                    AlreadyHaveOrderDialogFragment dialogFragment = new AlreadyHaveOrderDialogFragment();
                    dialogFragment.show(requireActivity().getSupportFragmentManager(), "ALREADY_HAVE_ORDER_DIALOG");
                } else {
                    setScanOptions();
                }
            }
        });
    }

    private void initRestaurantLauncher() {
        restaurantLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), RestaurantOrderMenuActivity.class);
                intent.putExtra(RESTAURANT_DATA, result.getContents());
                requireActivity().startActivity(intent);
            }
        });
    }

    private void initRecycler() {
        buildList(new OptionImageButtonModel[]{
                new OptionImageButtonModel(1, R.drawable.queue_action_background, getResources().getString(R.string.queue), () -> {
                    ((MainActivity) requireActivity()).openQueueActivity();
                }),
                new OptionImageButtonModel(2, R.drawable.restaurant_action_background, getResources().getString(R.string.restaurant), () -> {
                    viewModel.getActions();
                }),
                new OptionImageButtonModel(4, R.drawable.become_employee_background, getResources().getString(R.string.become_employee), () -> {
                    if (requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(APP_STATE, ANONYMOUS).equals(ANONYMOUS)) {
                        NeedAccountDialogFragment dialogFragment = new NeedAccountDialogFragment();
                        dialogFragment.show(requireActivity().getSupportFragmentManager(), "NEED_ACCOUNT_DIALOG");
                    } else {
                        Intent intent = new Intent(requireActivity(), BecomeEmployeeOptionsActivity.class);
                        requireActivity().startActivity(intent);
                    }
                })
        });
    }

    private void setScanOptions() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Qr-Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        restaurantLauncher.launch(scanOptions);
    }

    private void buildList(OptionImageButtonModel[] models) {
        list.addAll(Arrays.asList(models));
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(list);
    }
}