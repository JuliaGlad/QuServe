package com.example.myapplication.presentation.home.companyUser;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.example.myapplication.presentation.home.homeDelegates.homeQueueActionButton.QueueActionButtonDelegate;
import com.example.myapplication.presentation.home.homeDelegates.homeQueueActionButton.QueueActionButtonDelegateItem;
import com.example.myapplication.presentation.home.homeDelegates.homeQueueActionButton.QueueActionButtonModel;
import com.example.myapplication.presentation.home.homeDelegates.squareButton.SquareButtonDelegate;
import com.example.myapplication.presentation.home.homeDelegates.squareButton.SquareButtonDelegateItem;
import com.example.myapplication.presentation.home.homeDelegates.squareButton.SquareButtonModel;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeQueueCompanyUserViewModel.class);
        companyId = getArguments().getString(COMPANY_ID);
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
                if (models != null && models.size() != 0) {
                    initRecycler(models);
                } else {
                    initEmptyActionRecycler();
                }
            } else if (state instanceof HomeQueueCompanyState.Loading) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else if (state instanceof HomeQueueCompanyState.Error) {

            }
        });
    }

    private void initEmptyActionRecycler() {
        buildList(new DelegateItem[]{
                new AdviseBoxDelegateItem(new AdviseBoxModel(1, R.string.home_advise_box_text)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, R.string.add_employees, R.string.add_new_employees_to_your_company, R.drawable.ic_add_employee, this::showEmployeeQrCodeDialog)),
                new ButtonWithDescriptionDelegateItem(new ButtonWithDescriptionModel(2, R.string.create_queue, R.string.create_new_company_queue_so_people_can_join_it, R.drawable.ic_add_queue, () -> {
                    ((MainActivity) requireActivity()).openCreateCompanyQueueActivity(companyId);
                }))
        });
    }

    private void initRecycler(List<QueueCompanyHomeModel> models) {
        List<DelegateItem> delegates = new ArrayList<>();
        delegates.add(new SquareButtonDelegateItem(new SquareButtonModel(1, R.string.add_employees, R.string.create_queue, R.drawable.ic_add_employee, R.drawable.ic_add_queue,
                this::showEmployeeQrCodeDialog, () -> {
                ((MainActivity) requireActivity()).openCreateCompanyQueueActivity(companyId);
        })));
        delegates.addAll(addQueues(models));
        adapter.addDelegate(new SquareButtonDelegate());
        adapter.addDelegate(new QueueActionButtonDelegate());
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(delegates);
        binding.progressBar.setVisibility(View.GONE);
    }

    private List<DelegateItem> addQueues(List<QueueCompanyHomeModel> models) {
        List<DelegateItem> delegates = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            QueueCompanyHomeModel current = models.get(i);
            delegates.add(new QueueActionButtonDelegateItem(new QueueActionButtonModel(i + 1, current.getName(), String.valueOf(current.getParticipantsSize()), () -> {
                ((MainActivity)requireActivity()).openCompanyQueueDetailsActivity(companyId, current.getQueueId());
            })));
        }
        return delegates;
    }

    private void showEmployeeQrCodeDialog() {
        EmployeeQrCodeDialogFragment dialogFragment = new EmployeeQrCodeDialogFragment(companyId);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "EMPLOYEE_QE_CODE_DIALOG");
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = Arrays.asList(items);
        adapter.addDelegate(new AdviseBoxDelegate());
        adapter.addDelegate(new ButtonWithDescriptionDelegate());
        adapter.submitList(list);
    }

}