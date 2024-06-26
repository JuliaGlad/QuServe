package com.example.myapplication.presentation.home.basicUser;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_DATA;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.VISITOR;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_OWNER;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_RESTAURANT_VISITOR;
import static com.example.myapplication.presentation.utils.constants.Utils.OWNER;
import static com.example.myapplication.presentation.utils.constants.Utils.PARTICIPANT;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_DATA;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_NAME_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.databinding.FragmentHomeBasisUserBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.common.JoinQueueFragment.JoinQueueActivity;
import com.example.myapplication.presentation.dialogFragments.alreadyHaveOrder.AlreadyHaveOrderDialogFragment;
import com.example.myapplication.presentation.dialogFragments.alreadyOwnQueue.AlreadyOwnQueueDialogFragment;
import com.example.myapplication.presentation.dialogFragments.alreadyParticipateInQueue.AlreadyParticipateInQueueDialogFragment;
import com.example.myapplication.presentation.home.basicUser.model.CompanyBasicUserModel;
import com.example.myapplication.presentation.home.basicUser.model.HomeBasicUserModel;
import com.example.myapplication.presentation.home.basicUser.model.QueueBasicUserHomeModel;
import com.example.myapplication.presentation.home.basicUser.state.HomeBasicUserState;
import com.example.myapplication.presentation.home.companyUser.HomeQueueCompanyUserFragment;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton.HomeActionButtonModel;
import com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder.RestaurantOrderButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder.RestaurantOrderButtonModel;
import com.example.myapplication.presentation.home.recycler.homeDelegates.restaurantOrder.RestaurantOrderDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.CompanyUserFragment;
import com.example.myapplication.presentation.restaurantOrder.menu.RestaurantOrderMenuActivity;
import com.example.myapplication.presentation.service.ScanCode;
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
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegate;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionModel;

public class HomeBasisUserFragment extends Fragment {

    private HomeBasisUserViewModel viewModel;
    private FragmentHomeBasisUserBinding binding;
    private ActivityResultLauncher<ScanOptions> joinQueueLauncher, restaurantOrderLauncher;
    private ActivityResultLauncher<Intent>
            openMenuLauncher, orderDetailsLauncher,
            createQueueLauncher, queueDetailsLauncher,
            joinLauncher, waitingLauncher;
    private final List<DelegateItem> delegates = new ArrayList<>();
    private final MainAdapter adapter = new MainAdapter();
    private QueueBasicUserHomeModel participate, own, visitor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeBasisUserViewModel.class);
        initJoinQueueLauncher();
        initRestaurantOrderLauncher();
        initOpenMenuLauncher();
        initOrderDetailsLauncher();
        initCreateQueueLauncher();
        initQueueDetailsLauncher();
        initJoinLauncher();
        initWaitingLauncher();
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
        setAdapter();
        setupObserves();
    }

    private void initWaitingLauncher() {
        waitingLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            removeParticipateQueue();
                        }
                    }
                });
    }

    private void removeParticipateQueue() {
        for (int i = 0; i < delegates.size(); i++) {
            if (delegates.get(i) instanceof HomeActionButtonDelegateItem) {
                HomeActionButtonModel model = ((HomeActionButtonDelegateItem) delegates.get(i)).content();
                if (model.getType().equals(PARTICIPANT)) {
                    if (delegates.size() > 3) {
                        delegates.remove(i);
                        adapter.notifyItemRemoved(i);
                    } else {
                        int size = delegates.size();
                        delegates.clear();
                        adapter.notifyItemRangeRemoved(0, size);
                        initUserNoActionsRecycler(false);
                    }
                    break;
                }
            }
        }
    }

    private void initJoinLauncher() {
        joinLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name = data.getStringExtra(QUEUE_NAME_KEY);
                            if (!delegates.isEmpty()) {
                                delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(2, name, R.string.queue_participant, PARTICIPANT,
                                        () -> ((MainActivity) requireActivity()).launchQueueWaitingActivity(waitingLauncher))));
                                adapter.notifyItemInserted(delegates.size() - 1);
                            } else {
                                addBasicActionsDelegates();
                                delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(2, name, R.string.queue_participant, PARTICIPANT,
                                        () -> ((MainActivity) requireActivity()).launchQueueWaitingActivity(waitingLauncher))));
                                adapter.submitList(delegates);
                                adapter.onCurrentListChanged(adapter.getCurrentList(), delegates);
                            }
                        } else {
                            removeParticipateQueue();
                        }
                    }
                });
    }

    private void initQueueDetailsLauncher() {
        queueDetailsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            removeOwnQueue();
                        }
                    }
                });
    }

    private void removeOwnQueue() {
        for (int i = 0; i < delegates.size(); i++) {
            if (delegates.get(i) instanceof HomeActionButtonDelegateItem) {
                HomeActionButtonModel model = ((HomeActionButtonDelegateItem) delegates.get(i)).content();
                if (model.getType().equals(OWNER)) {
                    if (delegates.size() > 3) {
                        delegates.remove(i);
                        adapter.notifyItemRemoved(i);
                    } else {
                        int size = delegates.size();
                        delegates.clear();
                        adapter.notifyItemRangeRemoved(0, size);
                        initUserNoActionsRecycler(false);
                    }
                    break;
                }
            }
        }
    }

    private void initCreateQueueLauncher() {
        createQueueLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name = data.getStringExtra(QUEUE_NAME_KEY);
                            if (!delegates.isEmpty()) {
                                delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(3, name, R.string.queue_owner, OWNER,
                                        () -> ((MainActivity) requireActivity()).openQueueDetailsActivity(queueDetailsLauncher))));
                                adapter.notifyItemInserted(delegates.size() - 1);
                            } else {

                                addBasicActionsDelegates();
                                delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(3, name, R.string.queue_owner, OWNER,
                                        () -> ((MainActivity) requireActivity()).openQueueDetailsActivity(queueDetailsLauncher))));
                                adapter.submitList(delegates);
                                adapter.onCurrentListChanged(adapter.getCurrentList(), delegates);
                            }
                        } else {
                            removeOwnQueue();
                        }
                    }
                });
    }

    private void setAdapter() {
        adapter.addDelegate(new SquareButtonDelegate());
        adapter.addDelegate(new HomeActionButtonDelegate());
        adapter.addDelegate(new RestaurantOrderButtonDelegate());
        adapter.addDelegate(new AdviseBoxDelegate());
        adapter.addDelegate(new ButtonWithDescriptionDelegate());
        binding.recyclerView.setAdapter(adapter);
    }

    private void initOrderDetailsLauncher() {
        orderDetailsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        for (int i = 0; i < delegates.size(); i++) {
                            if (delegates.get(i) instanceof HomeActionButtonDelegateItem) {
                                HomeActionButtonModel model = ((HomeActionButtonDelegateItem) delegates.get(i)).content();
                                if (model.getType().equals(VISITOR)) {
                                    if (delegates.size() > 3) {
                                        delegates.remove(i);
                                        adapter.notifyItemRemoved(i);
                                    } else {
                                        int size = delegates.size();
                                        delegates.clear();
                                        adapter.notifyItemRangeRemoved(0, size);
                                        initUserNoActionsRecycler(false);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                });
    }

    private void initOpenMenuLauncher() {
        openMenuLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String id = data.getStringExtra(RESTAURANT);
                            String path = data.getStringExtra(PATH);
                            viewModel.getRestaurantNameById(id, path);
                        } else {
                            removeRestaurantVisitor();
                        }
                    }
                });
    }

    private void removeRestaurantVisitor() {
        for (int i = 0; i < delegates.size(); i++) {
            if (delegates.get(i) instanceof HomeActionButtonDelegateItem) {
                HomeActionButtonModel model = ((HomeActionButtonDelegateItem) delegates.get(i)).content();
                if (model.getType().equals(VISITOR)) {
                    if (delegates.size() > 3) {
                        delegates.remove(i);
                        adapter.notifyItemRemoved(i);
                    } else {
                        int size = delegates.size();
                        delegates.clear();
                        adapter.notifyItemRangeRemoved(0, size);
                        initUserNoActionsRecycler(false);
                    }
                    break;
                }
            }
        }
    }

    private void initRestaurantOrderLauncher() {
        restaurantOrderLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), RestaurantOrderMenuActivity.class);
                intent.putExtra(RESTAURANT_DATA, result.getContents());
                openMenuLauncher.launch(intent);
            }
        });
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

        viewModel.queueIdOwner.observe(getViewLifecycleOwner(), queueId -> {
            if (queueId != null) {
                showAlreadyOwnDialog(queueId);
            }
        });

        viewModel.restaurantName.observe(getViewLifecycleOwner(), bundle -> {
            if (bundle != null){
                String name = bundle.getString(RESTAURANT_NAME);
                String path = bundle.getString(PATH);

                if (!delegates.isEmpty()) {
                    delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(delegates.size(), name, R.string.restaurant_visitor, VISITOR,
                            () -> ((MainActivity) requireActivity()).openOrderDetailsActivity(path, orderDetailsLauncher))));
                    adapter.notifyItemInserted(delegates.size() - 1);
                } else {
                    addBasicActionsDelegates();
                    delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(delegates.size(), name, R.string.restaurant_visitor, VISITOR,
                            () -> ((MainActivity) requireActivity()).openOrderDetailsActivity(path, orderDetailsLauncher))));
                    adapter.submitList(delegates);
                    adapter.onCurrentListChanged(adapter.getCurrentList(), delegates);
                }
            }
        });

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof HomeBasicUserState.Success) {
                HomeBasicUserModel model = ((HomeBasicUserState.Success) state).data;

                if (model != null) {
                    participate = model.getParticipateQueue();
                    own = model.getOwnQueue();
                    visitor = model.getRestaurantVisitor();
                    initUserRecycler(model.getModels());
                } else {
                    initUserNoActionsRecycler(true);
                }

            } else if (state instanceof HomeBasicUserState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof HomeBasicUserState.Error) {
                binding.progressBar.getRoot().setVisibility(View.GONE);
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> viewModel.getUserBooleanData());
    }

    private void setScanOptions(ActivityResultLauncher<ScanOptions> launcher) {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Qr-Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        launcher.launch(scanOptions);
    }

    private void initUserRecycler(List<CompanyBasicUserModel> companies) {

        addBasicActionsDelegates();
        getActionDelegates(companies, participate, own, visitor);

        adapter.submitList(delegates);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.errorLayout.setVisibility(View.GONE);
    }

    private void addBasicActionsDelegates() {
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
                        ((MainActivity) requireActivity()).openCreateQueueActivity(createQueueLauncher);
                    } else {
                        viewModel.getQueueData();
                    }
                })));
        delegates.add(new RestaurantOrderDelegateItem(new RestaurantOrderButtonModel(3, () -> {
            if (visitor == null || visitor.getName().equals(NOT_RESTAURANT_VISITOR)) {
                setScanOptions(restaurantOrderLauncher);
            } else {
                AlreadyHaveOrderDialogFragment dialogFragment = new AlreadyHaveOrderDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "ALREADY_HAVE_ORDER_DIALOG");
            }
        })));
    }

    private void getActionDelegates(List<CompanyBasicUserModel> companies, QueueBasicUserHomeModel participate, QueueBasicUserHomeModel own, QueueBasicUserHomeModel restaurantVisitor) {
        if (companies != null && !companies.isEmpty()) {
            for (CompanyBasicUserModel current : companies) {
                delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(3, current.getName(), R.string.company_owner, COMPANY_OWNER,
                        () -> {
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                            if (current.getService().equals(QUEUE)) {
                                sharedPreferences.edit().putString(APP_STATE, COMPANY).apply();
                            } else {
                                sharedPreferences.edit().putString(APP_STATE, RESTAURANT).apply();
                            }
                            sharedPreferences.edit().putString(COMPANY_ID, current.getId()).apply();

                            Bundle bundle = new Bundle();
                            bundle.putString(COMPANY_ID, current.getId());

                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.slide_in_fast_anim, R.anim.slide_out_fast_anim)
                                    .replace(R.id.home_container, HomeQueueCompanyUserFragment.class, bundle)
                                    .setReorderingAllowed(true)
                                    .commit();
                        })));
            }
        }
        if (own != null) {
            delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(2, own.getName(), R.string.queue_owner, OWNER,
                    () -> ((MainActivity) requireActivity()).openQueueDetailsActivity(queueDetailsLauncher))));
        }

        if (participate != null) {
            delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(3, participate.getName(), R.string.queue_participant, PARTICIPANT,
                    () -> ((MainActivity) requireActivity()).launchQueueWaitingActivity(waitingLauncher))));
        }

        if (restaurantVisitor != null) {
            delegates.add(new HomeActionButtonDelegateItem(new HomeActionButtonModel(4, restaurantVisitor.getName(), R.string.restaurant_visitor, VISITOR,
                    () -> ((MainActivity) requireActivity()).openOrderDetailsActivity(restaurantVisitor.getId(), orderDetailsLauncher))));
        }
    }

    private void initUserNoActionsRecycler(boolean isDefault) {
        buildList(new DelegateItem[]{
                new AdviseBoxDelegateItem(new AdviseBoxModel(1, R.string.home_advise_box_text)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.join_queue), getString(R.string.scan_queue_s_qr_code_and_join_it), R.drawable.qr_code, this::setJoinQueueScanOptions)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.create_queue), getString(R.string.create_your_own_queue_so_people_can_join_it), R.drawable.ic_add_queue,
                        () -> ((MainActivity) requireActivity()).openCreateQueueActivity(createQueueLauncher))),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.order_in_restaurant), getString(R.string.scan_table_s_qr_code_open_restaurant_menu_and_create_order), R.drawable.ic_create_restaurant_order,
                        () -> setScanOptions(restaurantOrderLauncher)))
        }, isDefault);

    }

    private void initJoinQueueLauncher() {
        joinQueueLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), JoinQueueActivity.class);
                intent.putExtra(QUEUE_DATA, result.getContents());
                joinLauncher.launch(intent);
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
        AlreadyParticipateInQueueDialogFragment dialogFragment = new AlreadyParticipateInQueueDialogFragment();
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "ALREADY_PARTICIPATE_IN_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {

        });
    }

    private void showAlreadyOwnDialog(String queueId) {
        AlreadyOwnQueueDialogFragment dialogFragment = new AlreadyOwnQueueDialogFragment(queueId);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "ALREADY_OWN_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> ((MainActivity) requireActivity()).openCreateQueueActivity(createQueueLauncher));
    }

    private void buildList(DelegateItem[] items, boolean isDefault) {
        List<DelegateItem> list = Arrays.asList(items);
        adapter.submitList(list);
        if (!isDefault) {
            adapter.onCurrentListChanged(delegates, list);
        } else {
            binding.progressBar.getRoot().setVisibility(View.GONE);
            binding.errorLayout.errorLayout.setVisibility(View.GONE);
        }
    }
}