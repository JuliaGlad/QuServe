package com.example.myapplication.presentation.home.companyUser;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.IS_DEFAULT;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_NAME_KEY;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeQueueCompanyUserBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.dialogFragments.employeeQrCode.EmployeeQrCodeDialogFragment;
import com.example.myapplication.presentation.home.companyUser.models.QueueCompanyHomeModel;
import com.example.myapplication.presentation.home.companyUser.state.HomeQueueCompanyState;

import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegate;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.buttonWithDescription.ButtonWithDescriptionModel;

import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueActionButton.QueueActionButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueActionButton.QueueActionButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueActionButton.QueueActionButtonModel;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueCompanyDelegate.HomeCompanyQueueDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueCompanyDelegate.HomeCompanyQueueDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueCompanyDelegate.HomeCompanyQueueModel;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegate;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton.SquareButtonModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegate;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;

public class HomeQueueCompanyUserFragment extends Fragment {

    private String companyId = null;
    private HomeQueueCompanyUserViewModel viewModel;
    private FragmentHomeQueueCompanyUserBinding binding;
    private final MainAdapter adapter = new MainAdapter();
    private ActivityResultLauncher<Intent> createQueueLauncher, queueDetailsLauncher;
    private final List<DelegateItem> delegates = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeQueueCompanyUserViewModel.class);
        companyId = requireArguments().getString(COMPANY_ID);
        setLauncher();
        setDetailsLauncher();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeQueueCompanyUserBinding.inflate(inflater, container, false);
        viewModel.getQueues(companyId);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof HomeQueueCompanyState.Success) {
                List<QueueCompanyHomeModel> models = ((HomeQueueCompanyState.Success) state).data;
                if (models != null && !models.isEmpty()) {
                    initRecycler(models, true);
                } else {
                    initEmptyActionRecycler(true);
                }
            } else if (state instanceof HomeQueueCompanyState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof HomeQueueCompanyState.Error) {
                binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
                binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
                    viewModel.getQueues(companyId);
                });
            }
        });
    }


    private void setDetailsLauncher() {
        queueDetailsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            boolean isActive = data.getBooleanExtra(IS_DEFAULT, true);
                            String queueId = data.getStringExtra(QUEUE_ID);
                            if (!isActive) {
                                removeQueueFromDelegates(queueId);
                            }
                        }
                    }
                });
    }

    private void removeQueueFromDelegates(String queueId) {
        for (int i = 0; i < delegates.size(); i++) {
            if (delegates.get(i) instanceof HomeCompanyQueueDelegateItem) {
                HomeCompanyQueueModel model = (HomeCompanyQueueModel) delegates.get(i).content();
                if (model.getQueueId().equals(queueId)) {
                    if (delegates.size() > 2) {
                        delegates.remove(i);
                        adapter.notifyItemRemoved(i);
                        break;
                    } else {
                        int size = delegates.size();
                        delegates.clear();
                        adapter.notifyItemRangeRemoved(0, size);
                        initEmptyActionRecycler(false);
                    }
                }
            }
        }
    }

    private void setLauncher() {
        createQueueLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name = data.getStringExtra(QUEUE_NAME_KEY);
                            String queueId = data.getStringExtra(QUEUE_ID);
                            if (name != null) {
                                if (!delegates.isEmpty()) {
                                    delegates.add(new HomeCompanyQueueDelegateItem(new HomeCompanyQueueModel(delegates.size(), queueId, name, "0",
                                            () -> ((MainActivity) requireActivity()).openCompanyQueueDetailsActivity(companyId, queueId, queueDetailsLauncher))));
                                    adapter.notifyItemInserted(delegates.size() - 1);
                                } else {
                                    QueueCompanyHomeModel model = new QueueCompanyHomeModel(queueId, name, 0);
                                    initRecycler(new ArrayList<>(Collections.singletonList(model)), false);
                                }
                            } else {
                                removeQueueFromDelegates(queueId);
                            }
                        }
                    }
                });
    }

    private void initEmptyActionRecycler(boolean isDefault) {
        buildList(new DelegateItem[]{
                new AdviseBoxDelegateItem(new AdviseBoxModel(1, R.string.home_advise_box_text)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.add_employees), getString(R.string.add_new_employees_to_your_company), R.drawable.ic_add_employee, this::showEmployeeQrCodeDialog)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, getString(R.string.create_queue), getString(R.string.create_new_company_queue_so_people_can_join_it), R.drawable.ic_add_queue, () -> {
                    ((MainActivity) requireActivity()).launchCreateCompanyQueueActivity(companyId, createQueueLauncher);
                }))
        }, isDefault);
    }

    private void initRecycler(List<QueueCompanyHomeModel> models, boolean isDefault) {
        delegates.add(new SquareButtonDelegateItem(new SquareButtonModel(1, R.string.add_employees, R.string.create_queue, R.drawable.ic_add_employee, R.drawable.ic_add_queue,
                this::showEmployeeQrCodeDialog, () -> {
            ((MainActivity) requireActivity()).launchCreateCompanyQueueActivity(companyId, createQueueLauncher);
        })));
        delegates.addAll(addQueues(models));
        setAdapter();
        if (isDefault) {
            adapter.submitList(delegates);
            binding.progressBar.getRoot().setVisibility(View.GONE);
            binding.errorLayout.getRoot().setVisibility(View.GONE);
        } else {
            adapter.submitList(delegates);
            adapter.onCurrentListChanged(adapter.getCurrentList(), delegates);
        }
    }

    private void setAdapter() {
        adapter.addDelegate(new SquareButtonDelegate());
        adapter.addDelegate(new HomeCompanyQueueDelegate());
        binding.recyclerView.setAdapter(adapter);
    }

    private List<DelegateItem> addQueues(List<QueueCompanyHomeModel> models) {
        List<DelegateItem> delegates = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            QueueCompanyHomeModel current = models.get(i);
            delegates.add(new HomeCompanyQueueDelegateItem(new HomeCompanyQueueModel(i + 1, current.getQueueId(), current.getName(), String.valueOf(current.getParticipantsSize()),
                    () -> ((MainActivity) requireActivity()).openCompanyQueueDetailsActivity(companyId, current.getQueueId(), queueDetailsLauncher))));
        }
        return delegates;
    }

    private void showEmployeeQrCodeDialog() {
        EmployeeQrCodeDialogFragment dialogFragment = new EmployeeQrCodeDialogFragment(companyId);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "EMPLOYEE_QE_CODE_DIALOG");
    }

    private void buildList(DelegateItem[] items, boolean isDefault) {
        List<DelegateItem> list = Arrays.asList(items);
        adapter.addDelegate(new AdviseBoxDelegate());
        adapter.addDelegate(new ButtonWithDescriptionDelegate());
        adapter.submitList(list);
        if (isDefault) {
            binding.recyclerView.setAdapter(adapter);

            binding.errorLayout.errorLayout.setVisibility(View.GONE);
            binding.progressBar.getRoot().setVisibility(View.GONE);
        } else {
            adapter.onCurrentListChanged(adapter.getCurrentList(), list);
        }
    }

}