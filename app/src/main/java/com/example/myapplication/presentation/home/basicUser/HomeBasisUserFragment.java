package com.example.myapplication.presentation.home.basicUser;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.OWNER;
import static com.example.myapplication.presentation.utils.Utils.PARTICIPANT;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_DATA;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_DATA;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBasisUserBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.dialogFragments.alreadyOwnQueue.AlreadyOwnQueueDialogFragment;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonModel;

import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegate;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionModel;

import com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder.RestaurantOrderButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder.RestaurantOrderButtonModel;
import com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder.RestaurantOrderDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonModel;
import com.example.myapplication.presentation.home.basicUser.model.CompanyBasicUserModel;
import com.example.myapplication.presentation.home.basicUser.model.HomeBasicUserModel;
import com.example.myapplication.presentation.home.basicUser.model.QueueBasicUserHomeModel;
import com.example.myapplication.presentation.home.basicUser.state.HomeBasicUserState;
import com.example.myapplication.presentation.common.JoinQueueFragment.JoinQueueActivity;
import com.example.myapplication.presentation.profile.createAccount.firstFragment.CreateAccountFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.CompanyUserFragment;
import com.example.myapplication.presentation.restaurantOrder.menu.RestaurantOrderMenuActivity;
import com.example.myapplication.presentation.service.ScanCode;
import com.example.myapplication.presentation.service.queue.QueueActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;

public class HomeBasisUserFragment extends Fragment {

    private HomeBasisUserViewModel viewModel;
    private FragmentHomeBasisUserBinding binding;
    private ActivityResultLauncher<ScanOptions> joinQueueLauncher, restaurantOrderLauncher;
    private final MainAdapter adapter = new MainAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeBasisUserViewModel.class);
        initJoinQueueLauncher();
        initRestaurantOrderLauncher();
    }

    private void initRestaurantOrderLauncher() {
        restaurantOrderLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), RestaurantOrderMenuActivity.class);
                intent.putExtra(RESTAURANT_DATA, result.getContents());
                requireActivity().startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel.getUserBooleanData();
        binding = FragmentHomeBasisUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }

    private void setupObserves() {
        viewModel.getRestaurant.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                viewModel.getQueueByParticipantPath();
            }
        });

        viewModel.getCompanies.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                viewModel.getQueueByAuthorId();
            }
        });

        viewModel.getQueueById.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                viewModel.getRestaurantByVisitorPath();
            }
        });

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof HomeBasicUserState.Success) {
                HomeBasicUserModel model = ((HomeBasicUserState.Success) state).data;
                if (model != null) {
                    initUserRecycler(model.getModels(), model.getParticipateQueue(), model.getOwnQueue());
                } else {
                    initUserNoActionsRecycler();
                }

            } else if (state instanceof HomeBasicUserState.Loading) {
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof HomeBasicUserState.Error) {
                binding.progressBar.setVisibility(View.GONE);
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.progressBar.setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getUserBooleanData();
        });
    }

    private void initUserRecycler(List<CompanyBasicUserModel> companies, QueueBasicUserHomeModel participate, QueueBasicUserHomeModel own) {
        List<DelegateItem> delegates = new ArrayList<>();
        List<HomeActionButtonDelegateItem> actions = getActionDelegates(companies, participate, own);
        delegates.add(new SquareButtonDelegateItem(new SquareButtonModel(1, R.string.join_queue, R.string.create_queue, R.drawable.qr_code, R.drawable.ic_add_queue,
                () -> {
                    if (participate == null) {
                        setJoinQueueScanOptions();
                    } else {
                        showAlreadyParticipateDialog();
                    }
                },
                () -> {
                    if (own == null) {
                        ((MainActivity) requireActivity()).openCreateQueueActivity();
                    } else {
                        showAlreadyOwnDialog();
                    }
                })));
        delegates.add(new RestaurantOrderDelegateItem(new RestaurantOrderButtonModel(3, restaurantOrderLauncher)));
        delegates.addAll(actions);

        adapter.addDelegate(new SquareButtonDelegate());
        adapter.addDelegate(new HomeActionButtonDelegate());
        adapter.addDelegate(new RestaurantOrderButtonDelegate());
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(delegates);
        binding.progressBar.setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.GONE);
    }

    private List<HomeActionButtonDelegateItem> getActionDelegates(List<CompanyBasicUserModel> companies, QueueBasicUserHomeModel participate, QueueBasicUserHomeModel own) {
        List<HomeActionButtonDelegateItem> delegates = new ArrayList<>();

        if (companies != null && !companies.isEmpty()) {
            for (CompanyBasicUserModel current : companies) {
                delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(3, current.getName(), R.string.company_owner, OWNER,
                        () -> {
                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.nav_host_fragment_activity_main, CompanyUserFragment.class, null)
                                    .commit();
                        })));
            }
        }

        if (participate != null) {
            delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(2, participate.getName(), R.string.queue_participant, PARTICIPANT, () -> {
                ((MainActivity) requireActivity()).openQueueWaitingActivity();
            })));
        }

        if (own != null) {
            delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(3, own.getName(), R.string.queue_owner, OWNER, () -> {
                ((MainActivity) requireActivity()).openQueueDetailsActivity();
            })));
        }

        return delegates;
    }

    private void initUserNoActionsRecycler() {
        buildList(new DelegateItem[]{
                new AdviseBoxDelegateItem(new AdviseBoxModel(1, R.string.home_advise_box_text)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.join_queue), getString(R.string.scan_queue_s_qr_code_and_join_it), R.drawable.qr_code, this::setJoinQueueScanOptions)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.create_queue), getString(R.string.create_your_own_queue_so_people_can_join_it), R.drawable.ic_add_queue, () -> {
                    ((MainActivity) requireActivity()).openCreateQueueActivity();
                }))
        });

    }

    private void initJoinQueueLauncher() {
        joinQueueLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), JoinQueueActivity.class);
                intent.putExtra(QUEUE_DATA, result.getContents());
                requireActivity().startActivity(intent);
            }
        });
    }

    private void setJoinQueueScanOptions() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Qr-Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        joinQueueLauncher.launch(scanOptions);
    }

    private void showAlreadyParticipateDialog() {
        AlreadyOwnQueueDialogFragment dialogFragment = new AlreadyOwnQueueDialogFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "ALREADY_PARTICIPATE_IN_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {

        });
    }

    private void showAlreadyOwnDialog() {
        AlreadyOwnQueueDialogFragment dialogFragment = new AlreadyOwnQueueDialogFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "ALREADY_OWN_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            ((QueueActivity) requireActivity()).openCreateQueueActivity();
        });
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = Arrays.asList(items);
        adapter.addDelegate(new AdviseBoxDelegate());
        adapter.addDelegate(new ButtonWithDescriptionDelegate());
        binding.recyclerView.setAdapter(adapter);
        binding.progressBar.setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.GONE);
        adapter.submitList(list);
    }
}