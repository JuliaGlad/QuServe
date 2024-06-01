package com.example.myapplication.presentation.home.anonymousUser;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.VISITOR;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_RESTAURANT_VISITOR;
import static com.example.myapplication.presentation.utils.constants.Utils.PARTICIPANT;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_DATA;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_DATA;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAnonymousUserBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.common.JoinQueueFragment.JoinQueueActivity;
import com.example.myapplication.presentation.dialogFragments.alreadyHaveOrder.AlreadyHaveOrderDialogFragment;
import com.example.myapplication.presentation.home.anonymousUser.models.AnonymousUserActionsHomeModel;
import com.example.myapplication.presentation.home.anonymousUser.state.AnonymousUserState;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonModel;
import com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder.RestaurantOrderButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonModel;
import com.example.myapplication.presentation.restaurantOrder.menu.RestaurantOrderMenuActivity;
import com.example.myapplication.presentation.service.ScanCode;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegate;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionModel;

public class AnonymousUserFragment extends Fragment {

    private AnonymousUserViewModel viewModel;
    private FragmentAnonymousUserBinding binding;
    private String restaurantPath;
    private final MainAdapter adapter = new MainAdapter();
    private ActivityResultLauncher<ScanOptions> joinQueueLauncher, restaurantOrderLauncher;
    private ActivityResultLauncher<Intent> orderDetailsLauncher;
    private final List<DelegateItem> items = new ArrayList<>();
    private final List<DelegateItem> actions = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AnonymousUserViewModel.class);
        viewModel.getActions();
        initJoinQueueLauncher();
        initRestaurantOrderLauncher();
        initOrderDetailsLauncher();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAnonymousUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        setupObserves();
    }

    private void initAdapter() {
        adapter.addDelegate(new HomeActionButtonDelegate());
        adapter.addDelegate(new SquareButtonDelegate());
        adapter.addDelegate(new AdviseBoxDelegate());
        adapter.addDelegate(new ButtonWithDescriptionDelegate());
        adapter.addDelegate(new RestaurantOrderButtonDelegate());
        binding.recyclerView.setAdapter(adapter);
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

    private void initOrderDetailsLauncher() {
        orderDetailsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i) instanceof  HomeActionButtonDelegateItem){
                                HomeActionButtonModel model = ((HomeActionButtonDelegateItem)items.get(i)).content();
                                if (model.getType().equals(VISITOR)){
                                    items.remove(i);
                                    adapter.notifyItemRemoved(i);
                                    if (items.size() == 2){
                                        items.clear();
                                        adapter.notifyItemRangeRemoved(0, 2);
                                        initNoActionsRecycler(false);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                });
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

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof AnonymousUserState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof AnonymousUserState.Error) {
                setErrorLayout();

            } else if (state instanceof AnonymousUserState.ActionsGot) {
                AnonymousUserActionsHomeModel actions = ((AnonymousUserState.ActionsGot) state).data;
                String queuePath = actions.getQueueParticipant();
                restaurantPath = actions.getRestaurantVisitor();
                viewModel.getQueueByPath(queuePath);
            } else if (state instanceof AnonymousUserState.QueueDataGot) {
                String name = ((AnonymousUserState.QueueDataGot) state).queueName;
                if (!name.equals(NOT_PARTICIPATE_IN_QUEUE)) {
                    actions.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(0, name, R.string.queue_participant, PARTICIPANT,
                            () -> {
                                ((MainActivity) requireActivity()).openQueueWaitingActivity();
                            })));
                }
                viewModel.getOrderByPath(restaurantPath);
            } else if (state instanceof AnonymousUserState.RestaurantDataGot) {
                String name = ((AnonymousUserState.RestaurantDataGot) state).name;
                if (!name.equals(NOT_RESTAURANT_VISITOR)) {
                    actions.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(1, name, R.string.restaurant_visitor, PARTICIPANT,
                            () -> {
                                ((MainActivity) requireActivity()).openOrderDetailsActivity(restaurantPath, orderDetailsLauncher);
                            })));
                }

                if (!actions.isEmpty()) {
                    initRecycler();
                } else {
                    initNoActionsRecycler(true);
                }
                binding.progressBar.getRoot().setVisibility(View.GONE);
                binding.errorLayout.errorLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setErrorLayout() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getActions();
        });
    }

    private void initNoActionsRecycler(boolean isDefault) {
        items.add(new AdviseBoxDelegateItem(new AdviseBoxModel(0, R.string.here_you_will_see_all_your_available_actions)));
        items.add(new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(1, getString(R.string.join_queue), getString(R.string.scan_queue_s_qr_code_and_join_it), R.drawable.qr_code,
                () -> {
                    setScanOptions(joinQueueLauncher);
                })));
        items.add(new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.order_in_restaurant), getString(R.string.scan_table_s_qr_code_open_restaurant_menu_and_create_order), R.drawable.ic_create_restaurant_order,
                () -> {
                    if (restaurantPath.equals(NOT_RESTAURANT_VISITOR)) {
                        setScanOptions(restaurantOrderLauncher);
                    } else {
                        AlreadyHaveOrderDialogFragment dialogFragment = new AlreadyHaveOrderDialogFragment();
                        dialogFragment.show(requireActivity().getSupportFragmentManager(), "ALREADY_HAVE_ORDER_DIALOG");
                    }
                })));

        if (!isDefault){
            adapter.notifyItemRangeInserted(0, 3);
        } else {
            adapter.submitList(items);
        }
    }

    private void initRecycler() {
        items.add(new SquareButtonDelegateItem(new SquareButtonModel(2, R.string.join_queue, R.string.order_in_restaurant, R.drawable.qr_code, R.drawable.ic_create_restaurant_order,
                () -> {
                    setScanOptions(joinQueueLauncher);
                },
                () -> {
                    if (restaurantPath.equals(NOT_RESTAURANT_VISITOR)) {
                        setScanOptions(restaurantOrderLauncher);
                    } else {
                        AlreadyHaveOrderDialogFragment dialogFragment = new AlreadyHaveOrderDialogFragment();
                        dialogFragment.show(requireActivity().getSupportFragmentManager(), "ALREADY_HAVE_ORDER_DIALOG");
                    }
                })));
        items.addAll(actions);
        adapter.submitList(items);
    }

    private void setScanOptions(ActivityResultLauncher<ScanOptions> launcher) {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Qr-Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        launcher.launch(scanOptions);
    }
}